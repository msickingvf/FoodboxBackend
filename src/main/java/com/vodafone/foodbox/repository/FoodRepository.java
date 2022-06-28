package com.vodafone.foodbox.repository;

import org.springframework.data.repository.CrudRepository;
import com.vodafone.foodbox.model.Food;

public interface FoodRepository extends CrudRepository<Food, Integer>{

}
