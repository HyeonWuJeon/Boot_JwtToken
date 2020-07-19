package com.pack.jwt.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String type;
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String type, String title, String content) {
        this.type = type;

        this.title = title;
        this.content = content;
    }
}
