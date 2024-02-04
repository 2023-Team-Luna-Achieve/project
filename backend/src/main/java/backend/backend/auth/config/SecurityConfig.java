package backend.backend.auth.config;

import backend.backend.auth.jwt.filter.JwtExtractUtil;
import backend.backend.auth.jwt.filter.JwtFilter;
import backend.backend.auth.jwt.handler.JwtAccessDeniedHandler;
import backend.backend.auth.jwt.handler.JwtAuthenticationEntryPoint;
import backend.backend.auth.jwt.token.TokenProvider;
import backend.backend.auth.oauth2.CustomOAuth2UserService;
import backend.backend.auth.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import backend.backend.auth.oauth2.OAuth2AuthenticationFailureHandler;
import backend.backend.auth.oauth2.OAuth2AuthenticationSuccessHandler;
import backend.backend.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final ActuatorProperties actuatorProperties;
    private static final String ADMIN = "ADMIN";
    private final TokenProvider tokenProvider;
    private final JwtExtractUtil jwtExtractUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                /* swagger v3 */
                "/v3/api-docs/**",
                "/swagger-ui/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors() // CORS 설정 활성화
                .and()
                .csrf().disable() // CSRF 보호를 무시할 경로 패턴
                .formLogin().disable()
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
                .antMatchers("/").permitAll() // oauth2 로그인 테스트
                .antMatchers("/api/user/sign-in").permitAll() // 로그인
                .antMatchers("/api/user/sign-up").permitAll() // 회원가입
                .antMatchers("/api/email/verification/request").permitAll() // 이메일 인증요청
                .antMatchers("/api/email/verification/confirm").permitAll() // 인증번호 확인
                .antMatchers("/api/user/refresh").permitAll() // 로그인
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/actuator/*").permitAll()
//                .antMatchers("/api/notice")
//                .hasRole(ADMIN)
                .anyRequest().authenticated()// 그 외 인증 없이 접근X
                .and()
                .addFilterBefore(new JwtFilter(jwtExtractUtil, customUserDetailsService, tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/api/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .and().build();
    }
}
