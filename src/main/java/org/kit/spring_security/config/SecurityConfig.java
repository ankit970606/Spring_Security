package org.kit.spring_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
       //IT WILL CONNECT TO THE DATABASE AND CHECK WHETHER THE USER IS IN THE DATA BASE OR NOT
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());  FOR THE PLAIN TEXT AS IT IS MENTION IN THE PASSWORD SECTION
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return authenticationProvider;
    }




    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        //To disable csrf
//        http.csrf(customizer ->customizer.disable());
//        //for authorization
//        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
//        //for login page
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        //for making Stateless IT WILL GENERATE NEW SESSION EVERY TIME BUT FROM THE WEB YOU CAN NOT LOGIN FROM THE POSTMAN YOU HAVE TO CHECK
//        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//


        //or we can use this
        http
                .csrf(customizer ->customizer.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("register","login")
                        .permitAll()
                        .anyRequest().authenticated())
              //  .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

      }

      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
      }




//      @Bean
//      public UserDetailsService userDetailsService() {
//          UserDetails user = User
//                  .withDefaultPasswordEncoder()
//                  .username("ankit")
//                  .password("1234")
//                  .roles("USER")
//                  .build();
//          UserDetails admin = User
//                  .withDefaultPasswordEncoder()
//                  .username("aman")
//                  .password("1234")
//                  .roles("ADMIN")
//                  .build();
//          return new InMemoryUserDetailsManager(user, admin);
//      }

}
