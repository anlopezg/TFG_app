package es.udc.paproject.backend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtGenerator jwtGenerator;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(Customizer.withDefaults())
			.csrf((csrf) -> csrf.disable())
			.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtFilter(jwtGenerator), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests((authorize) -> authorize

					/********************* USER CONTROLLER *********************/
					.requestMatchers(HttpMethod.POST, "/users/signUp").permitAll()
					.requestMatchers(HttpMethod.POST, "/users/login").permitAll()
					.requestMatchers(HttpMethod.POST, "/users/loginFromServiceToken").permitAll()
					.requestMatchers(HttpMethod.POST, "/users/*/changePassword").hasAnyRole("USER", "SELLER")

					.requestMatchers(HttpMethod.PUT, "/users/*").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.PUT, "/users/*/becomeSeller").hasRole("USER")

					.requestMatchers(HttpMethod.DELETE, "/users/*").hasAnyRole("USER", "SELLER")

					/********************* CATALOG CONTROLLER *********************/
					.requestMatchers(HttpMethod.GET, "/catalog/crafts").permitAll()
					.requestMatchers(HttpMethod.GET, "/catalog/categories").permitAll()

					/********************* PUBLICATION CONTROLLER *********************/
					.requestMatchers(HttpMethod.POST, "/publications/*/patterns").hasRole("SELLER")
					.requestMatchers(HttpMethod.POST, "/publications/physicals").hasRole("SELLER")

					.requestMatchers(HttpMethod.GET, "/publications/*").hasRole("SELLER")
					.requestMatchers(HttpMethod.GET, "/publications/*/patterns").hasRole("SELLER")
					.requestMatchers(HttpMethod.GET, "/publications/*/patterns/*").hasRole("SELLER")
					.requestMatchers(HttpMethod.GET, "/publications/physicals/*").hasRole("SELLER")

					.requestMatchers(HttpMethod.PUT, "/publications/*/patterns/*").hasRole("SELLER")
					.requestMatchers(HttpMethod.DELETE, "/publications/*/patterns/*").hasRole("SELLER")


				.anyRequest().denyAll());

		return http.build();

	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration config = new CorsConfiguration();
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		config.setAllowCredentials(true);
	    config.setAllowedOriginPatterns(Arrays.asList("*"));
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    
	    source.registerCorsConfiguration("/**", config);
	    
	    return source;
	    
	 }

}
