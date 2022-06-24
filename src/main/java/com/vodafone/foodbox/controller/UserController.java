package com.vodafone.foodbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.foodbox.model.LoginUser;
import com.vodafone.foodbox.service.LoginUserService;

@RestController // @Controller and @ResponseBody
public class UserController {
	@Autowired 
	private LoginUserService loginUserService;
	
	
	private class AuthenticationInfo{
		String email="";
		boolean authenticated=false;
		String message="Failed to authenticate";
	}
	@PostMapping("/isAuthenticated")
	public ResponseEntity<AuthenticationInfo> authenticate(@RequestBody LoginUser userToLogin) throws Exception{
		LoginUser loginUser = loginUserService.getUserByEmail(userToLogin.getEmail());
		AuthenticationInfo authenticationInfo = new AuthenticationInfo();
		if (loginUser==null) {
			System.out.println("user not found in db");
		} else if (!loginUser.getPassword().equals(userToLogin.getPassword())) {
			System.out.println("incorrect pw");
		}
		authenticationInfo.authenticated=true;
		authenticationInfo.email=userToLogin.getEmail();
		authenticationInfo.message="Welcome "+userToLogin.getEmail();
		return ResponseEntity.ok(authenticationInfo);
	}
	
}
