package backend.backend.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // CORS 설정 활성화
                .and()
                .csrf().disable() // CSRF 보호를 무시할 경로 패턴
                .authorizeRequests()
//                .antMatchers("/public/**").permitAll() // '/public/' 경로로 시작하는 요청은 모두 허용
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated() // 그 외의 요청은 인증이 필요
                .and()
                .formLogin() // 로그인 폼 활성화
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
//                    .logoutSuccessUrl("/")  // 로그아웃 활성화
                    .permitAll();
    }
}
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOriginPattern("*"); // 모든 Origin 허용
//        config.
//        config.setAllowCredentials(true);
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

