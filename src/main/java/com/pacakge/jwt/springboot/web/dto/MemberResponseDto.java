package com.pacakge.jwt.springboot.web.dto;

import com.pacakge.jwt.springboot.domain.user.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;


    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
    }
}
