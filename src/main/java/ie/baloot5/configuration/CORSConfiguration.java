package ie.baloot5.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:9090");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
//        config.addAllowedHeader("Content-Type");
//        config.addAllowedHeader("Accept");
//        config.addAllowedMethod("POST");
//        config.ad
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}