package com.gpc.vehiclemanagement.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
	@Value("${delete.url}")
	private String deleteUrl;
	
	@Value("${get.url}")
	private String getUrl;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())
        .authorizeHttpRequests(req -> req
                .requestMatchers(deleteUrl,getUrl).hasRole("ADMIN").anyRequest().authenticated()
               ).httpBasic(Customizer.withDefaults());
return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        
        UserDetails user = User.builder().username("user").password(passwordEncoder().encode("user@123")).roles("USER")
                .build();
        
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin@123")).roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user,admin);
    }
    
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-ui/**"));
    }
}