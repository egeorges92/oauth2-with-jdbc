package com.dazito.oauthexample.service;

import java.util.List;

import com.dazito.oauthexample.model.Client;

public interface ClientService {

	List<Client> findAll();

	Client findById(String id);

	boolean exists(Client client);

	void save(Client client);

	void update(Client client);

	void deleteById(String id);

	void deleteAll();
}
