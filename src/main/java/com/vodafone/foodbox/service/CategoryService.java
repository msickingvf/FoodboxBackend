package com.vodafone.foodbox.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.foodbox.model.Category;
import com.vodafone.foodbox.model.Food;
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
	public List<Category> getAllCategories() {
		List<Category> categoryList = new ArrayList<>();
		this.categoryRepository.findAll().forEach(category -> categoryList.add(category));
		return categoryList;
	}
	public Category getCategoryById(int id) {
		return this.categoryRepository.findCategoryById(id);
	}

	public void setCategory(Category category) {
		Category oldCategory = this.categoryRepository.findCategoryById(category.getId());
		oldCategory.setDescription(category.getDescription());
		oldCategory.setName(category.getName());
		this.categoryRepository.save(oldCategory);
	}
	public boolean deleteCategory(int id) {
		Category category = this.categoryRepository.findCategoryById(id);
		this.categoryRepository.delete(category);
		return true;
	}
	
}
