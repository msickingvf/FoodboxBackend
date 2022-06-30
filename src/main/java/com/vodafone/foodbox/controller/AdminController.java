package com.vodafone.foodbox.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.foodbox.model.AdminUser;
import com.vodafone.foodbox.model.Category;
import com.vodafone.foodbox.model.Food;
import com.vodafone.foodbox.model.LoginUser;
import com.vodafone.foodbox.service.AdminUserService;
import com.vodafone.foodbox.service.CategoryService;
import com.vodafone.foodbox.service.FoodService;

@RestController // @Controller and @ResponseBody
@CrossOrigin()
public class AdminController {
	@Autowired
	private AdminUserService adminUserService;
	@Autowired 
	FoodService foodService;
	@Autowired 
	CategoryService categoryService;
	
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
	@PostMapping("/admin/authenticated")
	public ResponseEntity<AuthenticationInfo> authenticate(@RequestBody AdminUser adminToLogin) throws Exception{
		AdminUser adminUser = adminUserService.getUserByEmail(adminToLogin.getEmail());
		AuthenticationInfo authenticationInfo = new AuthenticationInfo();
		if (adminUser==null) {
			System.out.println("user not found in db");
			authenticationInfo.setAuthenticated(false);
		} else if (!adminUser.getPassword().equals(adminToLogin.getPassword())) {
			System.out.println("incorrect pw");
			authenticationInfo.setAuthenticated(false);
		} else if (adminUser.getPassword().equals(adminToLogin.getPassword())) {
			authenticationInfo.setAuthenticated(true);
			authenticationInfo.setEmail(adminUser.getEmail());
			authenticationInfo.setMessage("Welcome "+adminUser.getEmail());
		}
		return ResponseEntity.ok(authenticationInfo);
	}
	
	@PostMapping("/admin/setFood")
	public ResponseEntity<Object> setFood(@RequestBody Food food){
		if (food.getId() != null) { //existing food modification
			System.out.println("Modify Existing food with id "+food.getId());
			foodService.setFood(food);
		} else {
			System.out.println("Insert New Food");
			foodService.insertFood(food);
		}
		return ResponseEntity.ok(true);
	}
	
	@PostMapping("/admin/setCategory")
	public ResponseEntity<Object> setFood(@RequestBody Category category){
		if (category.getId() != null) { //existing food modification
			System.out.println("Modify Existing category with id "+category.getId());
			categoryService.setCategory(category);
		} else {
			System.out.println("Insert New category");
			categoryService.insertCategory(category);
		}
		return ResponseEntity.ok(true);
	}
	
	@DeleteMapping("/admin/deleteFood/{id}")
	public ResponseEntity<Object> deleteFood(@PathVariable int id){
		System.out.println("Deleting Object id "+id);
		try {
			if(this.foodService.deleteFood(id))
				return ResponseEntity.ok("deletetion successful");
		}
		catch(EntityNotFoundException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch(Exception e)
		{
			//return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deletion failed");
	}
	
	@DeleteMapping("/admin/deleteCategory/{id}")
	public ResponseEntity<Object> deleteCategory(@PathVariable int id){
		System.out.println("Deleting Object id "+id);
		try {
			if(this.categoryService.deleteCategory(id))
				return ResponseEntity.ok("deletetion successful");
		}
		catch(EntityNotFoundException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch(Exception e)
		{
			//return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deletion failed");
	}
}
