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

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpSession httpSession;
    private final MemberRepository memberRepository;
    MemberSaveRequestDto memberSaveRequestDto = new MemberSaveRequestDto();


    @GetMapping("/all/login")
    public String dispLogin(Model model, @LoginUser String email)
    {
        model.addAttribute("memberForm", new MemberForm());

        return "adminlogin";
    }
//
    @PostMapping("/api/all/login")
    public String login(@Valid MemberForm form) {

        Member member = memberService.findMember(form.getEmail()); //이메일 삽입완료

        memberService.loadUserByUsername(member.getEmail());

        String tok = jwtTokenProvider.createToken(member.getEmail(), member.getRole().getKey());
//        System.out.println("토큰값"+ tok);
//        System.out.println("인증정보 조회"+jwtTokenProvider.getAuthentication(tok).getAuthorities());
//        System.out.println("사용자정보 조회"+jwtTokenProvider.getUserPk(tok));

        return "redirect:/";
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
