package com.vodafone.foodbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.foodbox.model.AdminUser;
import com.vodafone.foodbox.model.LoginUser;
import com.vodafone.foodbox.repository.AdminUserRepository;
import com.vodafone.foodbox.repository.LoginUserRepository;

@Service
public class AdminUserService {
	@Autowired
	private AdminUserRepository adminUserRepository;
	public void addUser(AdminUser loginUser) {
		adminUserRepository.save(loginUser);
		
	}
}
