package backend.backend.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
                .logout(); // 로그아웃 활성화
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // 모든 Origin 허용
        config.addAllowedMethod("*"); // 모든 HTTP Method 허용
        config.addAllowedHeader("*"); // 모든 Header 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}