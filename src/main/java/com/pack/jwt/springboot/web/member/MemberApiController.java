package com.jojoldu.book.springboot.web.member;


import com.pack.jwt.springboot.config.JwtTokenProvider;
import com.pack.jwt.springboot.domain.user.MemberRepository;
import com.pack.jwt.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberApiController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/checkEmail")
    public int checkEmail(@RequestBody String email){
        System.out.println("안들어오니????");
        return memberService.validateDuplicateMember(email);
    }



//    //ttest중중
//    //로그인
//    @PostMapping("/api/all/login")
//    public String login(@RequestBody Map<String, String> user) {
//        Member member = memberService.findMember(user.get("email"));
//
//        System.out.println("둘의 차이는 무엇이더냐!!!!");
//        System.out.println(member.getRoles());
//        System.out.println(member.getRole());
//        return jwtTokenProvider.createToken(member.getUsername(), member.getRole().getKey());
//    }
//    @PostMapping("/api/all/signup")
//    public Long MemberSignup2 (@RequestBody Map<String, String> user, BindingResult result){
////        if (result.hasErrors()) {
////            return "signUp";
////        }
//        log.info("_--------------------" + user.get("name"));
//        Member member = memberRepository.save(Member.builder()
//                .email(user.get("email"))
//                .password(passwordEncoder.encode(user.get("password")))
//                .phone(user.get("phone"))
//                .name(user.get("name"))
//                .roles(Collections.singletonList("ROLE_USER"))
//                .role(Role.USER)
//                .build());
//        log.info(member.toString());
//        return member.getId();
//    }
}
