package com.example.th3.config;

import com.example.th3.service.appuser.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class AppSecConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAppUserService appUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                ((UserDetailsService) appUserService)
        ).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(userBuilder.username("admin").password("123").roles("ADMIN").build());
//        manager.createUser(userBuilder.username("user").password("123").roles("USER").build());
//        return manager;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/user/**","/create-customer/**").hasAnyRole("USER","ADMIN")
                .and()
                .authorizeRequests().antMatchers("/admin/**","/delete-customer/**","/edit-customer/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        http.csrf().disable();
    }
}
