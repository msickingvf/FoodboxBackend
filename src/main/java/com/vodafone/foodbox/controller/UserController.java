package com.vodafone.foodbox.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.foodbox.model.Category;
import com.vodafone.foodbox.model.Food;
import com.vodafone.foodbox.model.FoodOrder;
import com.vodafone.foodbox.model.LoginUser;
import com.vodafone.foodbox.service.CategoryService;
import com.vodafone.foodbox.service.FoodService;
import com.vodafone.foodbox.service.LoginUserService;

@RestController // @Controller and @ResponseBody
@CrossOrigin()
public class UserController {
	@Autowired 
	private LoginUserService loginUserService;
	@Autowired
	private FoodService foodService;
	@Autowired
	private CategoryService categoryService;
	
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
	
	@PostMapping("/orderFood")
	public ResponseEntity<FoodOrder> orderFood(@RequestBody 
			//LoginUser userToLogin
			FoodOrder foodOrder
			) throws Exception{
		//LoginUser loginUser = loginUserService.getUserByEmail(userToLogin.getEmail());
		for (Food food : foodOrder.getFoodItems()) {
			System.out.println("da: "+food.getId());
		}
		return ResponseEntity.ok(foodOrder);
	}
	
	@GetMapping("getFoods")
	public List<Food> getFoods(){
		return this.foodService.getAllFoods();
	}
	
	@GetMapping("getCategories")
	public List<Category> getCategories(){
		return this.categoryService.getAllCategories();
	}
	
}
