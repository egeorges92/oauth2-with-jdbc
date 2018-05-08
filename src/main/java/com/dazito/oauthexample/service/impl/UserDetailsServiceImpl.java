package com.dazito.oauthexample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dazito.oauthexample.repository.UserDetailsRepository;

/**
 * Created by daz on 29/06/2017.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDetailsRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
                .findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }
}
