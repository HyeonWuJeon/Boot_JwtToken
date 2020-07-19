package com.pack.jwt.springboot.domain.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    private String name;
    private String email;
    private String pwd;
    private String password;
    private String phone;
    private String roles;
    private Role role;

}
