package com.pack.jwt.springboot.web;

import com.pack.jwt.springboot.config.JwtTokenProvider;
import com.pack.jwt.springboot.config.auth.LoginUser;
import com.pack.jwt.springboot.domain.user.Member;
import com.pack.jwt.springboot.service.MailDto;
import com.pack.jwt.springboot.service.MailService;
import com.pack.jwt.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

        if(user!=null){
            Member member = memberService.findMember(user);
            model.addAttribute("member", member);
        }

        else{
            model.addAttribute("member", user);
        }

        return "home";
    }





}
