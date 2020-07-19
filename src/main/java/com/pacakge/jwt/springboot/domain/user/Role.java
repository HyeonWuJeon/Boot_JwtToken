package com.pacakge.jwt.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    GOOGLE("ROLE_GOOGLE", "구글 사용자"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
