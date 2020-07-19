package com.pacakge.jwt.springboot.config.auth;

import com.pacakge.jwt.springboot.config.JwtAuthenticationFilter;
import com.pacakge.jwt.springboot.config.JwtTokenProvider;
import com.pacakge.jwt.springboot.domain.user.Role;
import com.pacakge.jwt.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.jojoldu.book.springboot.config.TestInterceptor;

@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final MemberService memberService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/static/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/api/member/**").hasAnyRole(Role.USER.name(), Role.GOOGLE.name())
                .antMatchers("/api/admin/**").hasAnyRole(Role.ADMIN.name())
                .and()
                .formLogin()
                .loginPage("/all/login")
//                .loginProcessingUrl("/all/login")
                .defaultSuccessUrl("/").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/all/logout"))
                .logoutSuccessUrl("/")
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                           UsernamePasswordAuthenticationFilter.class)
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);

    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new TestInterceptor()) //인터셉터 등록
//                .addPathPatterns("/**") //등록구간
//                .excludePathPatterns("/test/**/") //제외구간
//                .excludePathPatterns("/all/login"); //로그인 쪽은 예외처리를 한다.
//    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

}
