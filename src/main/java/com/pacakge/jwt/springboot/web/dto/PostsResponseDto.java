package com.pacakge.jwt.springboot.web.dto;

import com.pacakge.jwt.springboot.domain.posts.Posts;
import com.pacakge.jwt.springboot.domain.user.Member;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private Member member;
    private String type;
    private String title;
    private String content;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.type = entity.getType();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}
