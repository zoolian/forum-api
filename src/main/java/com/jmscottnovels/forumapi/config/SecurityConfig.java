package com.jmscottnovels.forumapi.config;

//import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3002"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()   // by default uses bean called corsConfigurationSource
                .authorizeHttpRequests()
                .requestMatchers("/", "/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/forum/**").permitAll()   // access(AuthorityAuthorizationManager.hasAuthority("SCOPE_forum.read"))
                .requestMatchers(HttpMethod.OPTIONS, "/forum/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/forum/**").permitAll() //access(AuthorityAuthorizationManager.hasAuthority("SCOPE_forum.write"))
                .requestMatchers(HttpMethod.PUT, "/forum/**").permitAll() //access(AuthorityAuthorizationManager.hasAuthority("SCOPE_forum.write"))
                .requestMatchers(HttpMethod.DELETE, "/forum/**").access(AuthorityAuthorizationManager.hasAuthority("SCOPE_forum.admin"))
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();

        // Send a 401 message to the browser (w/o this, you'll see a blank page)
        // Okta.configureResourceServer401ResponseBody(http);
        return http.build();
    }
}
