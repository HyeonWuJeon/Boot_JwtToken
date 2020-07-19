package com.pacakge.jwt.springboot.web;

import com.pacakge.jwt.springboot.config.JwtTokenProvider;
import com.pacakge.jwt.springboot.config.auth.LoginUser;
import com.pacakge.jwt.springboot.domain.user.Member;
import com.pacakge.jwt.springboot.service.MailDto;
import com.pacakge.jwt.springboot.service.MailService;
import com.pacakge.jwt.springboot.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {


    private final MemberService memberService;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/admin/mail/{id}")
    public String Mail(@PathVariable Long id, Model model) throws MessagingException {

        Member member = memberService.findMember(id);
        model.addAttribute("email", member);
        return "mail-template";
    }

    @PostMapping("/mails")
    public String execMail(MailDto mailDto) {
        System.out.println("mailDto = " + mailDto);
        mailService.mailSend(mailDto);

        return "home";
    }

    @GetMapping("/")
    public String index(@LoginUser String user, Model model){

        System.out.println("home--------------------------------");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication = " + authentication.getName());
        String u = jwtTokenProvider.getUserPk("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTU5NTE2NDU4MSwiZXhwIjoxNTk1MTY2MzgxfQ.7jDHZgyFc0Izs11tLT2nYMaH033pXKg0YmZGUOUsyt4");
        System.out.println("u " + u );

        Member member2 = memberService.findMember(u);
//        System.out.println("user.getClass().getName() = " + user.getClass().getName());
        System.out.println("member2.getRole() = " + member2.getRole().getKey().equals("ROLE_ADMIN"));
        System.out.println("member2.getRole().getKey() = " + member2.getRole().getKey());

        if(user!=null){
            Member member = memberService.findMember(user);
            model.addAttribute("member", member);
        }
        else{
            System.out.println("\"여긴가?\" = " + "여긴가?");
            model.addAttribute("member", member2);

        }

        return "home";
    }





}
