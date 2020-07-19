package com.pack.jwt.springboot.service;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;


    @Override
    public String toString() {
        return "MailDto{" +
                "address='" + address + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}