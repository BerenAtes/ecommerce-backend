package com.ecommerce.backend.config;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class Security {
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(Arrays.asList("PUT","GET","POST","DELETE"));
        //corsConfiguration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE));
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth-> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/product/**")
                            .hasAnyAuthority("Admin","Store");
                    auth.requestMatchers(HttpMethod.PUT, "/product/**")
                            .hasAnyAuthority("Admin","Store");
                    auth.requestMatchers(HttpMethod.DELETE, "/product/**")
                            .hasAnyAuthority("Admin","Store");
                    auth.requestMatchers(HttpMethod.POST, "/category/**")
                            .hasAnyAuthority("Admin","Store");
                    auth.requestMatchers(HttpMethod.PUT, "/category/**")
                            .hasAnyAuthority("Admin","Store");
                    auth.requestMatchers(HttpMethod.DELETE, "/category/**")
                            .hasAnyAuthority("Admin","Store");
                    auth.requestMatchers("/user/**").hasAuthority("Admin");
                    auth.requestMatchers("/product/**").hasAuthority("Admin");
                    auth.requestMatchers("/address/**").hasAuthority("Customer");
                    auth.requestMatchers("/category/**", "/product/**","/order/**").hasAnyAuthority("Admin", "Customer");

                })
                .cors(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()).build();
    }
}
