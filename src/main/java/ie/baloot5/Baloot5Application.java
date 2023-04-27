package ie.baloot5;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@SpringBootApplication
public class Baloot5Application {

	public static void main(String[] args) {
//		var digest = MessageDigest.getInstance("SHA-1");
//		byte[] result = Hex.decodeHex(Arrays.toString(digest.digest("asdfasdfasdf".getBytes())).toCharArray());
//		System.out.println(result);
		SpringApplication.run(Baloot5Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}
