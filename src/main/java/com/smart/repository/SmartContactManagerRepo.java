package com.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.model.User;

public interface SmartContactManagerRepo extends JpaRepository<User, Integer>{

}
