package com.vodafone.foodbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.foodbox.model.Category;
import com.vodafone.foodbox.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	public boolean insertCategory(Category category) {
		this.categoryRepository.save(category);
		return true;
	}
	public Category findCategoryByName(String string) {
		System.out.println("try finding category by name "+string);
		return this.categoryRepository.findByName(string);
	}

	
}