package com.tvshowtracker.config;

import com.tvshowtracker.security.JwtAuthenticationEntryPoint;
import com.tvshowtracker.security.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.tvshowtracker.security")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String AUTH_PATH = "/auth/**";

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    @Qualifier("customUserDetailService")
    private UserDetailsService userService;

    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(AUTH_PATH).permitAll()
                    .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthorizationFilter,
                             UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder.userDetailsService(userService)
                                    .passwordEncoder(passwordEncoder());
    }

    @Bean
    public FilterRegistrationBean<JWTAuthorizationFilter> filterRegistrationBean() {
        FilterRegistrationBean<JWTAuthorizationFilter> filterRegistrationBean
                = new FilterRegistrationBean<>();
        filterRegistrationBean.setEnabled(false);
        filterRegistrationBean.setFilter(jwtAuthorizationFilter);
        return filterRegistrationBean;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST, AUTH_PATH);
    }
}
