package com.pacakge.jwt.springboot.web.dto;

import com.pacakge.jwt.springboot.domain.user.Member;
import com.pacakge.jwt.springboot.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String name;
    private String password;
    private String email;
    private String phone;
    private Role role;
    private List<String> roles;

    public void SHA256_PassWord(String password) {
        this.password = password;
    }

    public void GIVE_Role(Role role) {
        this.role = role;
    }

    @Builder
    public MemberSaveRequestDto(String name, String password, String email, String phone, Role role, List<String> roles) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.roles = roles;
    }



    public Member toEntity(){
        return Member.builder()
                .name(name)
                .password(password)
                .email(email)
                .phone(phone)
                .role(role)
                .build();
    }
}

