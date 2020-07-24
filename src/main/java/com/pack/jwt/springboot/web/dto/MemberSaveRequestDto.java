package com.pack.jwt.springboot.web.dto;

import com.pack.jwt.springboot.config.JwtTokenProvider;
import com.pack.jwt.springboot.domain.user.Member;
import com.pack.jwt.springboot.domain.user.MemberRepository;
import com.pack.jwt.springboot.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
//@NoArgsConstructor
public class MemberSaveRequestDto{

    @Autowired
    JwtTokenProvider jwtTokenProvider;


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

