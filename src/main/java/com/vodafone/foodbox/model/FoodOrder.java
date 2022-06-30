package com.vodafone.foodbox.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

public class FoodOrder {
	private Set<Food> foodItems = new HashSet<>();
	private String email;
	private String name;
	private String street;
	private String city;
	private String creditCardNumber;
	private String creditCardMMYY;
	private String creditCardSecCode;
	private double totalPrice;
	private boolean ordered=false;

	public Set<Food> getFoodItems() {
		return foodItems;
	}
	public void setFoodItems(Set<Food> foodItems) {
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
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
