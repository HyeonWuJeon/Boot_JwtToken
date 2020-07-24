package com.pack.jwt.springboot.config.auth;

import com.pack.jwt.springboot.config.auth.dto.SessionUser;
import com.pack.jwt.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {//컨트롤러에서 들어오는 특정 파라매터 핸들러

    private final HttpSession httpSession;
    private final MemberService memberService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = String.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }
    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("LoginUserArgument 클래스 : resolveArgument 함수");
        log.debug("Authentication 권한 보기 : " + authentication.getPrincipal());
        try {
            if (!authentication.toString().contains("picture")) {
                log.debug("사용자 자체서비스 로그인");
                User user = (User) authentication.getPrincipal();
//                return authentication.getPrincipal().toString(); //토큰저장시 DB사용 x
                return memberService.findMember(user.getUsername()).getEmail(); 
            } else {
                log.debug("사용자 구글 로그인");
                System.out.println(authentication);
                SessionUser session = (SessionUser) httpSession.getAttribute("user");
                return session.getEmail();
            }
        }catch(ClassCastException e){
            log.debug("로그인 안함");
            return null;
        }
    }
}
