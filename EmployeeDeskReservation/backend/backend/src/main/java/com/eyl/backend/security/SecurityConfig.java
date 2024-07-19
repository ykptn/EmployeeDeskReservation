package com.eyl.backend.security;



import com.eyl.backend.service.serviceImplementations.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private EmployeeService employeeService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/companies/**").hasRole("MANAGER")
                                .requestMatchers("/api/departments/**").hasRole("MANAGER")
                                .requestMatchers("/api/desks/**").hasRole("MANAGER")
                                .requestMatchers("/api/employees/register").hasRole("MANAGER")
                                .requestMatchers("/api/employees/update/**").hasRole("MANAGER")
                                .requestMatchers("/api/employees/delete/**").hasRole("MANAGER")
                                .requestMatchers("/api/employees/employee-list").hasRole("MANAGER")
                                .requestMatchers("/api/reservations/history/**").hasRole("MANAGER")
                                .requestMatchers("/api/employees/changePassword/**").hasAnyRole("MANAGER","WORKER")
                                .requestMatchers("/api/reservations/reserve").hasAnyRole("MANAGER","WORKER")
                                .requestMatchers("/api/reservations/update/**").hasAnyRole("MANAGER","WORKER")
                                .requestMatchers("/api/reservations/delete/**").hasAnyRole("MANAGER","WORKER")
                                .requestMatchers("/api/employees/login").permitAll()


                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                                })
                )
                                .logout(logout -> logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessHandler((request, response, authentication) -> {
                                            response.setStatus(HttpServletResponse.SC_OK);
                                            response.getWriter().flush();
                                        })
                                        .permitAll()
                                );
        return http.build();
    }

}
