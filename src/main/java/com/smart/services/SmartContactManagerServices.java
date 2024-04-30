package com.smart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.User;
import com.smart.repository.SmartContactManagerRepo;


@Service
public class SmartContactManagerServices {
	
	@Autowired
	SmartContactManagerRepo smartRepo;
	public User saveUser(User user) {
		return smartRepo.save(user);
	}

}
