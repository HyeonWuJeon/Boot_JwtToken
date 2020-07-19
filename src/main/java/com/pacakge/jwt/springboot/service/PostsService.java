package com.pacakge.jwt.springboot.service;

import com.pacakge.jwt.springboot.domain.posts.Posts;
import com.pacakge.jwt.springboot.domain.posts.PostsRepository;
import com.pacakge.jwt.springboot.domain.user.Member;
import com.pacakge.jwt.springboot.domain.user.MemberRepository;
import com.pacakge.jwt.springboot.web.dto.PostsListResponseDto;
import com.pacakge.jwt.springboot.web.dto.PostsResponseDto;
import com.pacakge.jwt.springboot.web.dto.PostsSaveRequestDto;
import com.pacakge.jwt.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long id, PostsSaveRequestDto requestDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
        requestDto.setMember(member);
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getType(),requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
