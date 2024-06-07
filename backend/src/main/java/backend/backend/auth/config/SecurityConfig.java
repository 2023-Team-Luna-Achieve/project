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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http // CORS 설정 활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandlingConfigurer -> {
                            exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                            exceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler);
                        }
                )

                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .sessionManagement(SessionManagementConfigurer ->
                        SessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers("/api/email/verification/request").permitAll() // 이메일 인증요청
                            .requestMatchers("/api/email/verification/confirm").permitAll() // 인증번호 확인
                            .requestMatchers("/api/user/sign-in").permitAll() // 로그인
                            .requestMatchers("/api/user/sign-up").permitAll() // 회원가입
                            .requestMatchers("/api/user/refresh").permitAll()
                            .requestMatchers("/api/reports/*").hasRole("ADMIN")
                            .requestMatchers("/v3/**", "swagger-ui/**").permitAll()
                            .requestMatchers("/favicon.ico").permitAll()
                            .requestMatchers("/actuator/*").permitAll()
                            .anyRequest().authenticated();
                    //               .requestMatchers("/api/notice")
                    //               .hasRole(ADMIN)
                })
                .oauth2Login(oAuth2LoginConfigurer -> {
                    oAuth2LoginConfigurer.authorizationEndpoint(authorizationEndpointConfig -> {
                        authorizationEndpointConfig.baseUri("/api/oauth2/authorize");
                        authorizationEndpointConfig.authorizationRequestRepository(cookieAuthorizationRequestRepository());
                    });
                    oAuth2LoginConfigurer.redirectionEndpoint(redirectionEndpointConfig ->
                            redirectionEndpointConfig.baseUri("/api/oauth2/callback/*"));
                    oAuth2LoginConfigurer.userInfoEndpoint(userInfoEndpointConfig ->
                            userInfoEndpointConfig.userService(customOAuth2UserService));
                    oAuth2LoginConfigurer.successHandler(oAuth2AuthenticationSuccessHandler);
                    oAuth2LoginConfigurer.failureHandler(oAuth2AuthenticationFailureHandler);
                })
                .addFilterBefore(new JwtFilter(jwtExtractUtil, customUserDetailsService, tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
