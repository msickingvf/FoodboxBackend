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

}
