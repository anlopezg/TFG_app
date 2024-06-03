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
					.requestMatchers(HttpMethod.PUT, "/users/becomeSeller").hasRole("USER")

					.requestMatchers(HttpMethod.DELETE, "/users/*").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.GET, "/users/*").permitAll()

					/********************* CATALOG CONTROLLER *********************/
					.requestMatchers(HttpMethod.GET, "/catalog/**").permitAll()

					.requestMatchers(HttpMethod.POST, "/catalog/favorites").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.GET, "/catalog/favorites").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.GET, "/catalog/favorites/*").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.DELETE, "/catalog/favorites/*").hasAnyRole("USER", "SELLER")



					/********************* PUBLICATION CONTROLLER *********************/
					.requestMatchers(HttpMethod.POST, "/publications/physicals/create").hasRole("SELLER")

					.requestMatchers(HttpMethod.GET, "/publications/**").hasRole("SELLER")

					.requestMatchers(HttpMethod.PUT, "/publications/physicals/edit/*").hasRole("SELLER")
					.requestMatchers(HttpMethod.DELETE, "/publications/physicals/delete/*").hasRole("SELLER")

					/********************* PATTERN CONTROLLER *********************/
					.requestMatchers(HttpMethod.POST, "/patterns/create").hasRole("SELLER")

					.requestMatchers(HttpMethod.GET, "/patterns/**").hasRole("SELLER")

					.requestMatchers(HttpMethod.PUT, "/patterns/edit/*").hasRole("SELLER")
					.requestMatchers(HttpMethod.DELETE, "/patterns/delete/*").hasRole("SELLER")


					/********************* SHOPPING CONTROLLER *********************/
					.requestMatchers(HttpMethod.GET, "/shopping/**").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.POST, "/shopping/**").hasAnyRole("USER", "SELLER")

					/********************* REVIEW CONTROLLER *********************/
					.requestMatchers(HttpMethod.GET, "/reviews/products/*").permitAll()
					.requestMatchers(HttpMethod.POST, "/reviews/publish").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.GET, "/reviews/users").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.PUT, "/reviews/*").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.DELETE, "/reviews/*").hasAnyRole("USER", "SELLER")

					/********************* PAYPAL CONTROLLER *********************/
					.requestMatchers(HttpMethod.POST, "/api/paypal/pay").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/paypal/cancel").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/paypal/success").permitAll()
					.requestMatchers("/api/paypal/**").permitAll()

					/********************* PAYMENTS CONTROLLER *********************/
					.requestMatchers(HttpMethod.GET, "/api/payments/accounts/*").hasAnyRole("USER", "SELLER")
					.requestMatchers(HttpMethod.POST, "/api/payments/create").hasAnyRole("USER", "SELLER")


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
