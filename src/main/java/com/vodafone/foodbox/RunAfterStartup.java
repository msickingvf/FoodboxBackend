package com.vodafone.foodbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.vodafone.foodbox.model.*;
import com.vodafone.foodbox.service.AdminUserService;
import com.vodafone.foodbox.service.CategoryService;
import com.vodafone.foodbox.service.FoodService;
import com.vodafone.foodbox.service.LoginUserService;

@Component
public class RunAfterStartup {
	@Autowired
	private LoginUserService loginUserSerivce;
	@Autowired
	private AdminUserService adminUserSerivce;
	@Autowired
	private FoodService foodService;
	@Autowired
	private CategoryService categoryService;
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(value=1)
	public void addLoginUser() {
		System.out.println("ADDING LOGINUSER");
		LoginUser loginUser = new LoginUser();
		loginUser.setEmail("user1@foodbox.com");
		loginUser.setPassword("password");
		loginUserSerivce.addUser(loginUser);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(value=2)
	public void addAdminUser() {
		System.out.println("ADDING ADMINUSER");
		AdminUser adminUser = new AdminUser();
		adminUser.setEmail("admin@foodbox.com");
		adminUser.setPassword("adminsPassword");
		adminUserSerivce.addUser(adminUser);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(value=3)
	public void addCagetory() {
		System.out.println("ADDING CATEGORY GERMAN");
		Category category = new Category();
		category.setDescription("Finest German Food");
		category.setName("German");
		categoryService.insertCategory(category);
		
		Category category2 = new Category();
		category2.setDescription("Finest Italian");
		category2.setName("Italian");
		categoryService.insertCategory(category2);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Order(value=4)
	public void addFood() {
		System.out.println("ADDING FOOD CURRYWURST POMMER MAJO");
		Category category = categoryService.findCategoryByName("German");
		System.out.println("ADDING TO CATEGORY "+category.getName());
		Food food = new Food();
		food.setDescription("Germany sausage with curry and fries with majonaise");
		food.setName("Currywurst Pommes Majo");
		food.setEnabled(true);
		food.setCategory(category);
		food.setPrice(5.5);
		foodService.insertFood(food);
		
		Food food2 = new Food();
		food2.setDescription("Schweinshaxn with Sauerkraut and Kartoffelstampf");
		food2.setName("Bavarian Menu");
		food2.setEnabled(true);
		food2.setCategory(category);
		food2.setPrice(15.5);
		foodService.insertFood(food2);
		
		Food food3 = new Food();
		food3.setDescription("Pizza Margharita");
		food3.setName("Bavarian Menu");
		food3.setEnabled(true);
		Category category2 = categoryService.findCategoryByName("Italian");
		food3.setCategory(category2);
		food3.setPrice(10.5);
		foodService.insertFood(food3);
		
		Food food4 = new Food();
		food4.setDescription("Pasta with peperoni and tomato sauce");
		food4.setName("Pasta Fuego");
		food4.setEnabled(true);
		food4.setCategory(category2);
		food4.setPrice(10.5);
		foodService.insertFood(food4);
	}
	
}
