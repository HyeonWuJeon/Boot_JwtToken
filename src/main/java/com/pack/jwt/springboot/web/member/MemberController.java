package com.pack.jwt.springboot.web.member;

import com.pack.jwt.springboot.config.JwtTokenProvider;
import com.pack.jwt.springboot.config.auth.LoginUser;
import com.pack.jwt.springboot.domain.user.Member;
import com.pack.jwt.springboot.domain.user.MemberForm;
import com.pack.jwt.springboot.domain.user.MemberRepository;
import com.pack.jwt.springboot.service.MemberService;
import com.pack.jwt.springboot.web.dto.MemberResponseDto;
import com.pack.jwt.springboot.web.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final FindByIndexNameSessionRepository sessionRepository;
    private final MemberService memberService;


    @GetMapping("/all/login")
    public String dispLogin(Model model, @LoginUser String email)
    {
        model.addAttribute("memberForm", new MemberForm());

        return "adminlogin";
    }


    @GetMapping("/signup")
    public String createMember(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "signUp";
    }



    @PostMapping("/api/all/signup")
    public String MemberSignup (@Valid MemberForm form , BindingResult result){
        if (result.hasErrors()) {
            System.out.println(" result 뭐지? " +result);
            return "signUp";
        }
        System.out.println(" result 뭐지?error 밖 " +result);
        MemberSaveRequestDto member = new MemberSaveRequestDto();

        memberService.SignUp(member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(form.getPassword())
                .phone(form.getPhone())
                .roles(Collections.singletonList("ROLE_USER"))
                .role(form.getRole())
                .build());

        log.info("성공");

        return "adminlogin";
    }

    @GetMapping("/admin/member_list")
    public String adminMemberList(Model model){
        List<MemberResponseDto> members = memberService.findAllDesc();
        model.addAttribute("member",members);

        return "admin_memberlist";

    }
}
