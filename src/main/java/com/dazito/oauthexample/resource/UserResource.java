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

import com.dazito.oauthexample.model.User;
import com.dazito.oauthexample.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	UserService userService; // Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All
	// Users--------------------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);// You many decide to return
																			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// User--------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		LOGGER.debug("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			LOGGER.debug("User with id {} not found", id);
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a
	// User--------------------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder, HttpServletRequest request) {
		LOGGER.debug("Creating User " + user.getId());

		if (userService.exists(user)) {
			LOGGER.debug("A User with name {} already exist", user.getId());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		userService.save(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path(String.format("%s/{id}", request.getContextPath())).buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User
	// --------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
		LOGGER.debug("Updating User {}", id);

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			LOGGER.debug("User with id {} not found", id);
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		/*
		 * currentUser.setName(user.getName()); currentUser.setAge(user.getAge());
		 * currentUser.setSalary(user.getSalary());
		 */

		userService.update(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User
	// --------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") String id) {
		LOGGER.debug("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			LOGGER.debug("Unable to delete. User with id {} not found", id);
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		userService.deleteById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users
	// --------------------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		LOGGER.debug("Deleting All Users");

		userService.deleteAll();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}
