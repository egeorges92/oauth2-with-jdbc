package com.dazito.oauthexample.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dazito.oauthexample.entities.ClientDetailsEntity;
import com.dazito.oauthexample.model.Client;
import com.dazito.oauthexample.repository.ClientDetailsRepository;
import com.dazito.oauthexample.service.ClientService;
import com.dazito.oauthexample.utils.ModelConverter;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientDetailsRepository repository;

	@Override
	public List<Client> findAll() {
		return repository.findAll().stream().map(client -> ModelConverter.convert(client, Client.class))
				.collect(Collectors.toList());
	}

	@Override
	public Client findById(String id) {
		return repository.findById(id).map(client -> ModelConverter.convert(client, Client.class))
				.orElseThrow(() -> new EntityNotFoundException("Could not find " + id));
	}

	@Override
	public boolean exists(Client client) {
		return repository.findById(client.getId()).isPresent();
	}

	@Override
	public void save(Client client) {
		repository.save(ModelConverter.convert(client, ClientDetailsEntity.class));
	}

	@Override
	public void update(Client client) {
		repository.save(ModelConverter.convert(client, ClientDetailsEntity.class));
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
