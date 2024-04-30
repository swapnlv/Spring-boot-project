package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.Helper.Message;
import com.smart.model.User;
import com.smart.services.SmartContactManagerServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	SmartContactManagerServices scmServ;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("title", "SmartContactManager - Home");
		return "home";
	}
	@GetMapping("/about")
	public String about(Model model){
		model.addAttribute("title", "SmartContactManager - About");
		return "about";
		
	}
	@GetMapping("/signup")
	public String signup(Model model){
		model.addAttribute("title", "SmartContactManager - SignUp");
		model.addAttribute("user", new User());
		return "signup";
		
	}
	@PostMapping("/doRegister")
  	public String register(@Valid @ModelAttribute("user") User user,  BindingResult result, Model model,@RequestParam(value="agreement",defaultValue="false") boolean agreement, HttpSession session) {
		try{
			if(!agreement) {
			System.out.println("please agree to terms and conditions before clicking ");
			throw new Exception("please agree to terms and conditions before clicking ");
		}
		
		if(result.hasErrors()) {
			System.out.println("Error : "+result.toString());
			model.addAttribute(user);
			return "signup";
		}
		System.out.println(result);
		user.setRole("ROLE_NORMAL");
		user.setImageURL("default.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User resultUser= scmServ.saveUser(user);
		System.out.println("Register Triggered !!!");
		System.out.println(resultUser);
		System.out.println(agreement);
		session.setAttribute("message",(new Message("Your Account has been registered !!!", "alert-success")));
		model.addAttribute("user", new User());

		return "signup";
		
	}catch(Exception e){
		e.printStackTrace();
		model.addAttribute("user", user);
		session.setAttribute("message",(new Message("Something went wrong!!" + e.getMessage() , "alert-danger")));
//		session.removeAttribute("message");
		return "signup";
	}

	
	}
	@PostMapping("/resetForm")
    public String resetForm(@ModelAttribute("user") User user) {
        // Additional logic to reset form fields if needed
        return "redirect:/signup"; // Redirect to the signup page after resetting
    }
	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("title", "SmartContactManager - SignIn");
		return "login";
	}
	
}
