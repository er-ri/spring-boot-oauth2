package com.example.securitylogin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.example.securitylogin")
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
	
	   @Override
	   public void addViewControllers(ViewControllerRegistry registry) {
	      registry.addViewController("/register").setViewName("register");
	      registry.addViewController("/login").setViewName("login");
	      registry.addViewController("/admin/home").setViewName("admin/home");
	   }
	
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler(
	                "/webjars/**",
	                "/img/**",
	                "/css/**",
	                "/js/**")
	                .addResourceLocations(
	                        "classpath:/META-INF/resources/webjars/",
	                        "classpath:/static/img/",
	                        "classpath:/static/css/",
	                        "classpath:/static/js/");
	    }
	    
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**").allowedMethods("*");
	    }
}
