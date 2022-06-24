package com.vodafone.foodbox.repository;

import org.springframework.data.repository.CrudRepository;

import com.vodafone.foodbox.model.LoginUser;

public interface LoginUserRepository extends CrudRepository<LoginUser, Integer>{

	LoginUser findLoginUserByEmail(String email);

}
