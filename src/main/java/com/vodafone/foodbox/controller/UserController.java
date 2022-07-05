package com.vodafone.foodbox.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    private JavaMailSender emailSender; 
	
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
	
	private class RegisterInfo{
		public boolean registrationSuccess=false;
		private String message="";
		public boolean isRegistrationSuccess() {
			return registrationSuccess;
		}
		public void setRegistrationSuccess(boolean registrationSuccess) {
			this.registrationSuccess = registrationSuccess;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegisterInfo> register(@RequestBody LoginUser userToLogin) throws Exception{
		LoginUser loginUser = loginUserService.getUserByEmail(userToLogin.getEmail());
		RegisterInfo registerInfo = new RegisterInfo();
		if (loginUser==null) {
			System.out.println("User not yet existing");
			loginUserService.addUser(userToLogin);
			registerInfo.setRegistrationSuccess(true);
			registerInfo.setMessage("Successfully Registered");
		} else {
			registerInfo.setMessage("Registration Failed, User already Existing");
			System.out.println("User already existing");
		}
		return ResponseEntity.ok(registerInfo);
	}
	
	@PostMapping("/orderFood")
	public ResponseEntity<FoodOrder> orderFood(@RequestBody 
			FoodOrder foodOrder
			) throws Exception{
		for (Food food : foodOrder.getFoodItems()) {
			System.out.println("Ordered Food: "+food.getId()+": "+food.getName());
		}
		foodOrder.setOrdered(true);
		sendOrderMail(foodOrder);
		return ResponseEntity.ok(foodOrder);
	}
	
	private void sendOrderMail(FoodOrder foodOrder) {
		String mailHeadline = "<h3>New Food Order</h3>";
		String mailBody = "<h4>Order Details</h4>";
		int counter = 0;
		Iterator<Food> foodItems = foodOrder.getFoodItems().iterator();
		while (foodItems.hasNext()) {
			counter++;
			Food food = foodItems.next();
			mailBody = mailBody + "<li>"+counter+": "+food.getName()+",price: "+food.getPrice()+"</li>";
		}
		mailBody = mailBody + "Total Price: "+foodOrder.getTotalPrice();
		mailBody = mailBody + "<h4>Adress/Payment Info</h4>";
		mailBody = mailBody + "<p>Name: "+foodOrder.getName()+"</p>";
		mailBody = mailBody + "<p>Street: "+foodOrder.getStreet()+"</p>";
		mailBody = mailBody + "<p>City: "+foodOrder.getCity()+"</p>";
		mailBody = mailBody + "<p>Email: "+foodOrder.getEmail()+"</p>";
		mailBody = mailBody + "<p>CreditCardNumber: "+foodOrder.getCreditCardNumber()+"</p>";
		mailBody = mailBody + "<p>CreditCard ValidUntil: "+foodOrder.getCreditCardMMYY()+"</p>";
		mailBody = mailBody + "<p>CreditCard SecCode: "+foodOrder.getCreditCardSecCode()+"</p>";
		String mailText = mailHeadline+"\n"+mailBody;
		try {
			sendMail("new Order", mailText);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendMail(String mailSubject, String mailText) throws IllegalStateException, IOException, SQLException, Exception {
		System.out.println("Sending mail Subject: \""+mailSubject+"\"");
		MimeMessage msg = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("foodbox@sicking-net.de");
		helper.setFrom("foodbox@sicking-net.de");		
		helper.setSubject(mailSubject);
		helper.setText(mailText,true);
		emailSender.send(msg);
	}
	

	@GetMapping("getFoods")
	public List<Food> getFoods(){
		return this.foodService.getAllFoods();
	}
	
	@RequestMapping(path = "/getFoodById/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getFoodById(@PathVariable int id){
		return ResponseEntity.ok(foodService.getFoodById(id));
	}
	
	@RequestMapping(path = "/getCategoryById/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCategoryById(@PathVariable int id){
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}
	
	@GetMapping("getCategories")
	public List<Category> getCategories(){
		return this.categoryService.getAllCategories();
	}
	
}
