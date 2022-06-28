package com.vodafone.foodbox.repository;

import org.springframework.data.repository.CrudRepository;

import com.vodafone.foodbox.model.Category;
import com.vodafone.foodbox.model.Food;

public interface CategoryRepository extends CrudRepository<Category, Integer>{

	Category findByName(String string);

}
