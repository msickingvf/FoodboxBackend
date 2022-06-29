package com.vodafone.foodbox.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FoodOrder {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;	
	private ArrayList<Food> foodItems;
	private String email;
	private String name;
	private String street;
	private String city;
	private String creditCardNumber;
	private String creditCardMMYY;
	private String creditCardSecCode;
	private boolean ordered=false;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ArrayList<Food> getFoodItems() {
		return foodItems;
	}
	public void setFoodItems(ArrayList<Food> foodItems) {
		this.foodItems = foodItems;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCreditCardMMYY() {
		return creditCardMMYY;
	}
	public void setCreditCardMMYY(String creditCardMMYY) {
		this.creditCardMMYY = creditCardMMYY;
	}
	public String getCreditCardSecCode() {
		return creditCardSecCode;
	}
	public void setCreditCardSecCode(String creditCardSecCode) {
		this.creditCardSecCode = creditCardSecCode;
	}
	public boolean isOrdered() {
		return ordered;
	}
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}
	
}
