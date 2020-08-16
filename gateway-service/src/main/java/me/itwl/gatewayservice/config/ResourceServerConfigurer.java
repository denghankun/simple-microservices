package me.itwl.gatewayservice.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Created by Steven on 2019/10/27.
 */
@AllArgsConstructor
@EnableWebFluxSecurity
public class ResourceServerConfigurer {

    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().authenticated()
                .and().exceptionHandling()
                    .accessDeniedHandler(restfulAccessDeniedHandler)
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                    .csrf().disable()
        ;

        http.oauth2ResourceServer().jwt();
        http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);

        return http.build();
    }
}
