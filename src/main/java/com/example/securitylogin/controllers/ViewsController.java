package com.example.securitylogin.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.securitylogin.domain.MyUserDetails;
import com.example.securitylogin.domain.UserEntity;
import com.example.securitylogin.infra.line.api.v2.LineAPIService;
import com.example.securitylogin.infra.line.api.v2.response.AccessToken;
import com.example.securitylogin.infra.line.api.v2.response.IdToken;
import com.example.securitylogin.services.MyUserDetailsService;

@Controller
public class ViewsController {
	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	LineAPIService lineAPIService;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public ModelAndView goHome(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("User-Agent");

		ModelAndView mav = new ModelAndView("index");
		mav.addObject("ip", ip);
		mav.addObject("ua", userAgent);

		return mav;
	}

	@RequestMapping(value = "/link-line", method = RequestMethod.POST)
	public String linkLine() {
		return "redirect:" + lineAPIService.makeLineAuthorizationURL();
	}

	@RequestMapping(value = "/link-line-auth", method = RequestMethod.GET)
	public String getLineAuthInfo(Principal principal, ModelMap model,
			@RequestParam(value = "code", required = false) String code) {
		AccessToken accessToken = lineAPIService.issueAccessToken(code);
		IdToken idToken = lineAPIService.getProfileInformationFromIDtoken(accessToken);

		UserEntity user = myUserDetailsService.findByUserName(principal.getName());
		user.setLineId(idToken.sub);
		myUserDetailsService.updateUser(user);

		model.addAttribute("lineId", idToken.sub);
		return "admin/home";
	}

	@RequestMapping(value = "/oauth2-login", method = RequestMethod.GET)
	public String goAdminHome(ModelMap model,
			@AuthenticationPrincipal final OAuth2User oAuthUser,
			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		model.addAttribute("oAuthUserName", oAuthUser.getName());
		model.addAttribute("authorities", oAuthUser.getAuthorities());
		model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
		model.addAttribute("oAuthUserAttributes", oAuthUser.getAttributes());
		model.addAttribute("accessToken", authorizedClient.getAccessToken().getTokenValue());
		
		return "admin/home";
	}

	@RequestMapping(value = "/form-login", method = RequestMethod.GET)
	public String getFormLoginInfo(Model model, Authentication authentication) {
		model.addAttribute("userName", authentication.getName());
		model.addAttribute("authorities", authentication.getAuthorities());

		return "admin/home";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String register(@RequestParam(name = "username") String name,
			@RequestParam(name = "password") String password, Model model) {
		UserEntity user = new UserEntity();
		user.setUserName(name);
		user.setActive(true);
		user.setPassword(password);
		user.setAuthorities("ADMIN");
		myUserDetailsService.addUser(user);
		model.addAttribute("message", "User " + name + " has been created.");
		return "register";
	}
}
