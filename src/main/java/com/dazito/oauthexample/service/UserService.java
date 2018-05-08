package com.dazito.oauthexample.service;

import java.util.List;

import com.dazito.oauthexample.model.User;

public interface UserService {

	List<User> findAll();

	User findById(String id);

	boolean exists(User user);

	void save(User user);

	void update(User user);

	void deleteById(String id);

	void deleteAll();

}
