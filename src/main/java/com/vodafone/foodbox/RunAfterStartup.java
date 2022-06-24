package com.vodafone.foodbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.vodafone.foodbox.model.AdminUser;
import com.vodafone.foodbox.model.LoginUser;
import com.vodafone.foodbox.service.AdminUserService;
import com.vodafone.foodbox.service.LoginUserService;

@Component
public class RunAfterStartup {
	@Autowired
	private LoginUserService loginUserSerivce;
	@Autowired
	private AdminUserService adminUserSerivce;
	
	@EventListener(ApplicationReadyEvent.class)
	public void addLoginUser() {
		System.out.println("ADDING LOGINUSER");
		LoginUser loginUser = new LoginUser();
		loginUser.setEmail("user1@foodbox.com");
		loginUser.setPassword("usersPassword");
		loginUserSerivce.addUser(loginUser);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void addAdminUser() {
		System.out.println("ADDING ADMINUSER");
		AdminUser adminUser = new AdminUser();
		adminUser.setEmail("admin@foodbox.com");
		adminUser.setPassword("adminsPassword");
		adminUserSerivce.addUser(adminUser);
	}
}
