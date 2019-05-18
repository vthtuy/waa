package edu.mum.waa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

//@EnableGlobalMethodSecurity(prePostEnabled = false, securedEnabled = false)
@EnableWebSecurity
@Configuration
public class WaaSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    protected AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> l = new ArrayList<AuthenticationProvider>();
        l.add(jwtAuthenticationProvider);
        return new ProviderManager(l);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() throws Exception {

        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        filter.setAuthenticationFailureHandler(new JwtFailureHandler());
        return filter;
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/resources/**")
                .antMatchers("/retreat-checking/**")
                .antMatchers("/student-lookup/**")
                .antMatchers("/student-lookup/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .antMatchers("/retreat-checking/**").permitAll()
                .antMatchers("/student-lookup/**").permitAll()
                .antMatchers("/faculty-report/**").permitAll()
                .antMatchers("/report/**").permitAll();

        http.csrf().disable()
                .authorizeRequests().antMatchers("/api/authentication*").permitAll()
                .antMatchers("/api").authenticated()
                //.antMatchers("/retreat-checking/**").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
        http.headers().frameOptions().sameOrigin();


    }

}
