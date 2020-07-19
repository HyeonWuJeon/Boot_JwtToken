package com.pacakge.jwt.springboot.web.dto;

import com.pacakge.jwt.springboot.domain.posts.Posts;
import com.pacakge.jwt.springboot.domain.user.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String type;
    private String title;
    private Member member;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.title = entity.getTitle();
        this.member = entity.getMember();
        this.modifiedDate = entity.getModifiedDate();
    }
}
