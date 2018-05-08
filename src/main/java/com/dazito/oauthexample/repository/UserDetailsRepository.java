package com.dazito.oauthexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dazito.oauthexample.entities.UserDetailsEntity;

/**
 * Created by daz on 29/06/2017.
 */
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, String> {
}
