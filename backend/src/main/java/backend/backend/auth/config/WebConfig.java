package backend.backend.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class  WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("*") // 모든 HTTP Method 허용
                .allowedHeaders("*") // 모든 Header 허용
                .allowedOrigins("http://localhost:5173")
                .exposedHeaders("Authorization", "Refresh-Token");
    }
}
