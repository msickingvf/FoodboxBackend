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
		private String email="";
		private boolean authenticated=false;
		private String message="Failed to authenticate";
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isAuthenticated() {
			return authenticated;
		}
		public void setAuthenticated(boolean authenticated) {
			this.authenticated = authenticated;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
	@PostMapping("/authenticated")
	public ResponseEntity<AuthenticationInfo> authenticate(@RequestBody LoginUser userToLogin) throws Exception{
		LoginUser loginUser = loginUserService.getUserByEmail(userToLogin.getEmail());
		AuthenticationInfo authenticationInfo = new AuthenticationInfo();
		if (loginUser==null) {
			System.out.println("user not found in db");
			authenticationInfo.setAuthenticated(false);
		} else if (!loginUser.getPassword().equals(userToLogin.getPassword())) {
			System.out.println("incorrect pw");
			authenticationInfo.setAuthenticated(false);
		} else if (loginUser.getPassword().equals(userToLogin.getPassword())) {
			authenticationInfo.setAuthenticated(true);
			authenticationInfo.setEmail(userToLogin.getEmail());
			authenticationInfo.setMessage("Welcome "+userToLogin.getEmail());
		}
		return ResponseEntity.ok(authenticationInfo);
	}
	
}
