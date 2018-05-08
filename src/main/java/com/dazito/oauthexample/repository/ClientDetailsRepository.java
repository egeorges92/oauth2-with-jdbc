package com.dazito.oauthexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dazito.oauthexample.entities.ClientDetailsEntity;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetailsEntity, String>{

}
