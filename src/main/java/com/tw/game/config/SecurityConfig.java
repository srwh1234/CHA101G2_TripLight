package com.tw.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig{

//    @Autowired
//    private CustomAuthenticationProvider authenticationProvider;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider);
//    }

    // 設定使用者權限(暫存使用者)
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        // 建立使用者
        var jay = User.builder()
                .username("jay")
                .password("{noop}!!!!")
                .roles("MANAGER").build();

        return new InMemoryUserDetailsManager(jay);
    }


    // 設定Security Filter chain(設定HttpSecurity)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/game-end/manage.html").hasRole("MANAGER")
//                .requestMatchers(HttpMethod.PUT,"/users/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE,"/users").hasRole("MANAGER")
                .requestMatchers(HttpMethod.GET,"/users/all").hasRole("MANAGER")
                .anyRequest().permitAll();
        // 配置認證方式
        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}