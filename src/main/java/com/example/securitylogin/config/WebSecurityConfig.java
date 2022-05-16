package com.example.securitylogin.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
	
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.headers().frameOptions().sameOrigin().and()
			.authorizeRequests()
				.antMatchers("/", "/index","/register","/login","/css/**","/new","/webjars/**","/h2-console/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/form-login", true)
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				.and()
			.oauth2Login()
				.loginPage("/login")
				.defaultSuccessUrl("/oauth2-login",true)
				.authorizationEndpoint(authorizationEndpoint ->
                	authorizationEndpoint
                		.authorizationRequestResolver(
                			new CustomAuthorizationRequestResolver(this.clientRegistrationRepository))
				.baseUri("/login/oauth2/authorization"));
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
	
	public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
		
	    private final OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver;

	    public CustomAuthorizationRequestResolver(
	            ClientRegistrationRepository clientRegistrationRepository) {

	        this.defaultAuthorizationRequestResolver =
	                new DefaultOAuth2AuthorizationRequestResolver(
	                        clientRegistrationRepository, "/login/oauth2/authorization");
	    }
	    

	    @Override
	    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
	        OAuth2AuthorizationRequest authorizationRequest =
	                this.defaultAuthorizationRequestResolver.resolve(request); 

	        return authorizationRequest != null ? 
	                customAuthorizationRequest(authorizationRequest) :
	                null;
	    }

	    @Override
	    public OAuth2AuthorizationRequest resolve(
	            HttpServletRequest request, String clientRegistrationId) {

	        OAuth2AuthorizationRequest authorizationRequest =
	                this.defaultAuthorizationRequestResolver.resolve(
	                    request, clientRegistrationId);  

	        return authorizationRequest != null ?  
	                customAuthorizationRequest(authorizationRequest) :
	                null;
	    }

	    private OAuth2AuthorizationRequest customAuthorizationRequest(
	            OAuth2AuthorizationRequest authorizationRequest) {

	        Map<String, Object> additionalParameters =
	                new LinkedHashMap<>(authorizationRequest.getAdditionalParameters());
	        additionalParameters.put("bot_prompt", "normal");
	        additionalParameters.put("initial_amr_display", "lineqr");
	        additionalParameters.put("switch_amr", true);

	        return OAuth2AuthorizationRequest.from(authorizationRequest)    
	                .additionalParameters(additionalParameters) 
	                .build();
	    }

	}

    
}
