package com.erstedigital.meetingappbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/ws/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }

    // --- For postman testing only ---
/*     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                 .antMatchers("/**").permitAll().anyRequest().authenticated()
                 .and().formLogin().permitAll().and().logout().permitAll().and().httpBasic();
         http.cors().disable().csrf().disable();
     }*/

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable();
    }*/
    // ---------------------------------
}
