package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class MyConfig{
	 @Bean
	    public UserDetailsService userDetailsService() {
	        return new UserDetailServiceImpl();
	    }
	 
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    public DaoAuthenticationProvider daoAuthenticationProvider() {
	    	
	    	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	    	daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
	    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	    	
			return daoAuthenticationProvider;
	    	
	    }
	    
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
	    	http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN")
	    	.requestMatchers("/user/**").hasRole("NORMAL")
	    	.requestMatchers("/**").permitAll().and().formLogin()
	    	.loginPage("/signin")
	    	.loginProcessingUrl("/dologin")
	    	.defaultSuccessUrl("/user/index")
	    	.and().csrf().disable();
	    	
			return http.build();
	    	
	    }
}
