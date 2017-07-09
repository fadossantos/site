package br.com.moradas291.site.web.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.moradas291.site.business.entities.RequestUser;
import br.com.moradas291.site.web.mail.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class HomeController {

	@Autowired
	SmtpMailSender smtpMailSender;

	@RequestMapping(value="", method = RequestMethod.GET)
	public ModelAndView home(){
		return new ModelAndView("index");
	}

	@RequestMapping(value="/menuNavegacao", method = RequestMethod.GET)
	public ModelAndView menuNavegacao() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("fragments/_menuNavegacao");
		return mav;
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login() {		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/login");
		mav.addObject("formLogin", new RequestUser());
		mav.addObject("message", "");
		return mav;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/";
	}
	
	@RequestMapping(value="/enviaemail", method = RequestMethod.GET)
	public String enviaemail () {
	    try {
			smtpMailSender.send("denizeom@gmail.com", "teste de envio email", "Te amoooooooooooooooooooooooooooooooo");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "redirect:/";
	}
	
}
