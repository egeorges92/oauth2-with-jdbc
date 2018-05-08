package com.dazito.oauthexample.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dazito.oauthexample.model.Client;
import com.dazito.oauthexample.service.ClientService;

@RestController
@RequestMapping(path = "/client")
public class ClientResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientResource.class);

	@Autowired
	ClientService clientService; // Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All
	// Clients--------------------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<Client>> listAllClients() {
		List<Client> clients = clientService.findAll();
		if (clients.isEmpty()) {
			return new ResponseEntity<List<Client>>(HttpStatus.NO_CONTENT);// You many decide to return
																			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// Client--------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Client> getClient(@PathVariable("id") String id) {
		LOGGER.debug("Fetching Client with id {}", id);
		Client client = clientService.findById(id);
		if (client == null) {
			LOGGER.debug("Client with id {} not found", id);
			return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

	// -------------------Create a
	// Client--------------------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> createClient(@RequestBody Client client, UriComponentsBuilder ucBuilder,
			HttpServletRequest request) {
		LOGGER.debug("Creating Client " + client.getId());

		if (clientService.exists(client)) {
			LOGGER.debug("A Client with name {} already exist", client.getId());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		clientService.save(client);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path(String.format("%s/{id}", request.getContextPath()))
				.buildAndExpand(client.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Client
	// --------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Client> updateClient(@PathVariable("id") String id, @RequestBody Client client) {
		LOGGER.debug("Updating Client {}", id);

		Client currentClient = clientService.findById(id);

		if (currentClient == null) {
			LOGGER.debug("Client with id {} not found", id);
			return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
		}

		/*
		 * currentClient.setName(client.getName()); currentClient.setAge(client.getAge());
		 * currentClient.setSalary(client.getSalary());
		 */

		clientService.update(currentClient);
		return new ResponseEntity<Client>(currentClient, HttpStatus.OK);
	}

	// ------------------- Delete a Client
	// --------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Client> deleteClient(@PathVariable("id") String id) {
		LOGGER.debug("Fetching & Deleting Client with id {}", id);

		Client client = clientService.findById(id);
		if (client == null) {
			LOGGER.debug("Unable to delete. Client with id {} not found", id);
			return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
		}

		clientService.deleteById(id);
		return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Clients
	// --------------------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<Client> deleteAllClients() {
		LOGGER.debug("Deleting All Clients");

		clientService.deleteAll();
		return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
	}
}
