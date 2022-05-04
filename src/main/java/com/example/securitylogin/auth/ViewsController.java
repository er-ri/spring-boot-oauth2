package com.example.securitylogin.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ViewsController {
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@RequestMapping(value = {"/", "/index"}, method= RequestMethod.GET)
    public ModelAndView goHome(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("User-Agent");
		
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("ip", ip);
		mav.addObject("ua", userAgent);
		
        return mav;
    }

	@RequestMapping(value = "/oauth2-login", method= RequestMethod.GET)
    public String goAdminHome(ModelMap model, @AuthenticationPrincipal final OAuth2User user,
          @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		model.addAttribute("userName", user.getName());
		model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
		model.addAttribute("userAttributes", user.getAttributes());
		model.addAttribute("accessToken", authorizedClient.getAccessToken().getTokenValue());
        return "admin/home";
    }
			
	@RequestMapping(value = "/form-login", method= RequestMethod.GET)
	public String getFormLoginInfo(Model model) {
		return "admin/home";
	}
	
	@RequestMapping(value = "/new", method= RequestMethod.POST)
    public String register(HttpServletRequest request, Model model) {
		String name;
		String password;
		name = request.getParameter("username");
		password = request.getParameter("password");
		model.addAttribute("message", "Message set in controller");
		UserEntity user = new UserEntity();
		user.setId(1111);
		user.setUserName(name);
		user.setActive(true);
		user.setPassword(password);
		user.setRoles("ADMIN");
		myUserDetailsService.addUser(user);
        return "index";
    }
}
