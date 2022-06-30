package com.vodafone.foodbox.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.foodbox.model.Food;
import com.vodafone.foodbox.repository.FoodRepository;

@Service
public class FoodService {
	
	@Autowired
	private FoodRepository foodRepository;

	public List<Food> getAllFoods() {
		List<Food> foodList = new ArrayList<>();
		this.foodRepository.findAll().forEach(food -> foodList.add(food));
		return foodList;
	}

	public boolean insertFood(Food food) {
		this.foodRepository.save(food);
		return true;
	}

	public Food getFoodById(int id) {
		return this.foodRepository.findFoodById(id);
	}

	public void setFood(Food food) {
		Food oldFood = this.foodRepository.findFoodById(food.getId());
		oldFood.setCategory(food.getCategory());
		oldFood.setDescription(food.getDescription());
		oldFood.setEnabled(food.isEnabled());
		System.out.println("old name: "+oldFood.getName()+" new name: "+food.getName());
		oldFood.setName(food.getName());
		oldFood.setPictureUrl(food.getPictureUrl());
		oldFood.setPrice(food.getPrice());
		this.foodRepository.save(oldFood);
	}

	public boolean deleteFood(int id) {
		Food food = this.foodRepository.findFoodById(id);
		this.foodRepository.delete(food);
		return true;
	}

}
