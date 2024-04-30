package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smart.model.User;
import com.smart.services.SmartContactManagerServices;

public class SmartContactManagerController {

	
	@PostMapping("/saveUser")
	public void saveUser(@RequestBody User user) {
		
	}

	
	
}
