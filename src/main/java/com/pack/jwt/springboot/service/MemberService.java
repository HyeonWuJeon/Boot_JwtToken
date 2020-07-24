package com.pack.jwt.springboot.service;

import com.pack.jwt.springboot.config.JwtTokenProvider;
import com.pack.jwt.springboot.domain.user.Member;
import com.pack.jwt.springboot.domain.user.MemberRepository;
import com.pack.jwt.springboot.domain.user.Role;
import com.pack.jwt.springboot.web.dto.MemberResponseDto;
import com.pack.jwt.springboot.web.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService implements UserDetailsService, AuthenticationManager {

    private final MemberRepository memberRepository;
//    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long SignUp(MemberSaveRequestDto requestDto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        requestDto.SHA256_PassWord(passwordEncoder.encode(requestDto.getPassword()));
        requestDto.GIVE_Role(Role.USER);
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public int validateDuplicateMember(String user_email) {

        String value = user_email;
        value = value.substring(1, value.length() - 1);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] entry = value.split(":");
        hashMap.put(entry[0].trim(), entry[1].trim());
        String value2 = hashMap.values().toString().substring(2, hashMap.values().toString().length() - 2);
        Member findMember = memberRepository.findByEmail(value2)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + user_email));
        if (findMember != null) {
            return 1;
        } else {
            return 0;
        }
    }


    //해당부분이 없더라도스프링 시큐리티에서 구현해준다
    //하지만 패스워드 암호화 및 필터작업을 위해 직접 컨트롤한다.
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername 시작");
        Member userEntityWrapper = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + userEmail));
        if (userEntityWrapper == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }


        //인증 사용자명, 암호로 인증인스턴스 구현
        Authentication request = new UsernamePasswordAuthenticationToken(userEntityWrapper.getEmail(), userEntityWrapper.getPassword());
        //권한부여 (Admin, USer)
        GrantedAuthority authority = new SimpleGrantedAuthority(userEntityWrapper.getRole().getKey());
        UserDetails userDetails = (UserDetails) new User(userEntityWrapper.getEmail(),
                userEntityWrapper.getPassword(), Arrays.asList(authority));
        //검증을 위해 토큰을 Manager 인스턴스로 넘긴다.
        Authentication result = authenticate(request);

        //인증 성공시  컨텍스트에 담는다.
        SecurityContextHolder.getContext().setAuthentication(result);
        return userDetails;
    }



    @Transactional(readOnly = true)
    public Member findMember(Object id) {
        if (id instanceof Long) {
            Member member = memberRepository.findById((Long) id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));
            return member;
        } else {
            Member member = memberRepository.findByEmail((String) id)
                    .orElseThrow(()-> new IllegalArgumentException("해당 회원이 없습니다. id = "+ id));

            return member;
        }
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAllDesc() {
        return memberRepository.findAllDesc().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    //해당부분이 없더라도스프링 시큐리티에서 구현해준다.
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        log.debug("Authentication 인증 시작");
        Member member = memberRepository.findByEmail(auth.getName())
                        .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다 = auth.getName()"+auth.getName()));

        List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

        //          권한 추가
        AUTHORITIES.add(new SimpleGrantedAuthority(member.getRole().getKey()));

        //토큰 발행

//        jwtTokenProvider.createToken(member.getEmail(), member.getRole().getKey());

        // 인증 성공시 인스턴스 리턴 하여 시큐리티 컨텍스트홀더에 추가한다.
        return new UsernamePasswordAuthenticationToken(auth.getName(),
                auth.getCredentials(), AUTHORITIES);
    }
}
