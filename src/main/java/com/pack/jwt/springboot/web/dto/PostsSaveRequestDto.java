package com.pack.jwt.springboot.web.dto;

import com.pack.jwt.springboot.domain.posts.Posts;
import com.pack.jwt.springboot.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String type;
    private Member member;

    @Builder
    public PostsSaveRequestDto(Member member, String type, String title, String content) {
        this.member =member;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .type(type)
                .member(member)
                .build();
    }

}
