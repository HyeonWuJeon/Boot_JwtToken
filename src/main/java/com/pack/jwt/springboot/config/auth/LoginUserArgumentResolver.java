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
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

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
        System.out.println("resolveArgument 함수");
        System.out.println(authentication.getPrincipal());

        try {
            if (!authentication.toString().contains("picture")) {
                User user = (User) authentication.getPrincipal();
                System.out.println(" 여기다!!= " + user.getUsername());
//                return authentication.getPrincipal().toString(); //토큰만 사용시 인증 인터페이스에서 바로꺼내줘야한다.
                return memberService.findMember(user.getUsername()).getEmail(); //토큰만 사용할경우 db사용불가
            } else {
                System.out.println(authentication);
                SessionUser session = (SessionUser) httpSession.getAttribute("user");
                return session.getEmail();
            }
        }catch(ClassCastException e){
            return null;
        }
    }
}
