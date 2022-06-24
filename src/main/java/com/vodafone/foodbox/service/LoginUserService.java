package com.vodafone.foodbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.foodbox.model.LoginUser;
import com.vodafone.foodbox.repository.LoginUserRepository;

@Service
public class LoginUserService {

	@Autowired
	private LoginUserRepository loginUserRepository;
	public void addUser(LoginUser loginUser) {
		loginUserRepository.save(loginUser);
		
	}
	public LoginUser getUserByEmail(String email) {
		LoginUser loginUser = loginUserRepository.findLoginUserByEmail(email);
		return loginUser;
	}

}
