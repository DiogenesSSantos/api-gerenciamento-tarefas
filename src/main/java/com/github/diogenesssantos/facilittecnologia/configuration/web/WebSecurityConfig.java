package com.github.diogenesssantos.facilittecnologia.configuration.web;


import com.github.diogenesssantos.facilittecnologia.configuration.security.JwtAuthenticationFilter;
import com.github.diogenesssantos.facilittecnologia.configuration.security.JwtUtil;
import com.github.diogenesssantos.facilittecnologia.configuration.security.RestAuthenticationEntryPoint;
import com.github.diogenesssantos.facilittecnologia.configuration.security.RoleBasedAuthSuccessHandler;
import com.github.diogenesssantos.facilittecnologia.service.JpaUsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider dao(JpaUsuarioService service, PasswordEncoder encoder) {
        var dao = new DaoAuthenticationProvider(service);
        dao.setPasswordEncoder(encoder);
        return dao;

    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   RoleBasedAuthSuccessHandler successHandler,
                                                   JpaUsuarioService jpaUsuarioService,
                                                   DaoAuthenticationProvider authProvider,
                                                   JwtUtil jwtUtil,
                                                   RestAuthenticationEntryPoint restAuthenticationEntryPoint) throws Exception {

        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil);

        http.authenticationProvider(authProvider)
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e.authenticationEntryPoint(restAuthenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/tarefas/**").hasRole("ADMINISTRADOR")
                        .anyRequest().authenticated()
                ).userDetailsService(jpaUsuarioService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .successHandler(successHandler)
                        .permitAll()
                ).logout(Customizer.withDefaults());

        return http.build();
    }


}
