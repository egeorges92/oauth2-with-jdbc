package com.dazito.oauthexample.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dazito.oauthexample.entities.UserDetailsEntity;
import com.dazito.oauthexample.model.User;
import com.dazito.oauthexample.repository.UserDetailsRepository;
import com.dazito.oauthexample.service.UserService;
import com.dazito.oauthexample.utils.ModelConverter;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDetailsRepository repository;
	
	@Override
	public List<User> findAll() {
		return repository.findAll().stream().map(user -> ModelConverter.convert(user, User.class)).collect(Collectors.toList());
	}

	@Override
	public User findById(String id) {
		return repository.findById(id).map(user -> ModelConverter.convert(user, User.class))
                .orElseThrow(() -> new EntityNotFoundException("Could not find " + id));
	}

	@Override
	public boolean exists(User user) {
		return repository.findById(user.getId()).isPresent();
	}

	@Override
	public void save(User user) {
		repository.save(ModelConverter.convert(user, UserDetailsEntity.class));
	}

	@Override
	public void update(User user) {
		repository.save(ModelConverter.convert(user, UserDetailsEntity.class));
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

}
