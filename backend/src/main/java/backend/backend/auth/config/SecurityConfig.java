package backend.backend.auth.config;

import backend.backend.auth.jwt.filter.JwtFilter;
import backend.backend.auth.jwt.handler.JwtAccessDeniedHandler;
import backend.backend.auth.jwt.handler.JwtAuthenticationEntryPoint;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // CORS 설정 활성화
                .and()
                .csrf().disable() // CSRF 보호를 무시할 경로 패턴
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                .antMatchers("/swagger-ui").permitAll()
                .antMatchers("/api/signin").permitAll() // 로그인
                // api
                .antMatchers("/api/users/signup").permitAll() // 회원가입 api
                .antMatchers("/api/email/verification/request").permitAll() // 이메일 인증요청
                .antMatchers("/api/email/verification/confirm").permitAll() // 인증번호 확인

                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated();// 그 외 인증 없이 접근X

                http.addFilterBefore(new JwtFilter(customUserDetailsService, tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
