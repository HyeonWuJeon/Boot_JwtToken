package com.pack.jwt.springboot.web;

import com.pack.jwt.springboot.service.PostsService;
import com.pack.jwt.springboot.web.dto.PostsListResponseDto;
import com.pack.jwt.springboot.web.dto.PostsResponseDto;
import com.pack.jwt.springboot.web.dto.PostsSaveRequestDto;
import com.pack.jwt.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PostsApiController {

    private final PostsService postsService;

    // 저장
    @PostMapping("/api/member/posts/{id}")
    public Long save(@PathVariable Long id, @RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(id, requestDto);
    }

    // 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    //삭제
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    //조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    //전체조회
    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }


}
