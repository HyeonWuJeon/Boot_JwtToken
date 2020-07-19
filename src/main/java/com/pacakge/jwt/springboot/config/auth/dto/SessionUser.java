package com.pacakge.jwt.springboot.config.auth.dto;

import com.pacakge.jwt.springboot.domain.user.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Setter
@Getter
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionUser implements Serializable {

    private String name;
    private String email;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
