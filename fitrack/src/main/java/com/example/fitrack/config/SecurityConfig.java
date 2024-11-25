    package com.example.fitrack.config;

    import com.example.fitrack.filter.JwtAuthenticationFilter;
    import com.example.fitrack.services.UserDetailsImplementation;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.HttpStatusEntryPoint;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        private final UserDetailsImplementation userDetailsServiceImp;

        private final JwtAuthenticationFilter jwtAuthenticationFilter;


        public SecurityConfig(UserDetailsImplementation userDetailsServiceImp,
                              JwtAuthenticationFilter jwtAuthenticationFilter) {
            this.userDetailsServiceImp = userDetailsServiceImp;
            this.jwtAuthenticationFilter = jwtAuthenticationFilter;

        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                            req->req.requestMatchers("/api/login/**").permitAll()
                                    .requestMatchers("/api/register/**").hasAuthority("ADMIN")
                                    .requestMatchers("/api/admin_only/**").hasAuthority("ADMIN")
                                    .requestMatchers("/user_only/**").hasAuthority("user")
                                    .anyRequest().permitAll()
                    ).userDetailsService(userDetailsServiceImp)
                    .sessionManagement(session->session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }

    }