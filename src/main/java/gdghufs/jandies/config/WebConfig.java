package gdghufs.jandies.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(
                // TODO 프로덕션 환경 자동 감지
                /* DEV */
                "http://localhost", // backend
                "http://localhost:3000", // frontend
                /* PRODUCTION */
                "https://api.jandi.es", // backend
                "https://jandi.es" // frontend
            )
            .allowedMethods("*"); // 필요한 메서드 추가
    }
}
