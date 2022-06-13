package com.product.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("rahul".equals(username)) {
            return new User("rahul", "$2a$10$zLl2rz13dpX7PXHoq8naW.V77WCciqufiQgbOST.I2LUQpCx5j.hC", List.of());
        } else {
            System.err.println("thrown");
            throw new UsernameNotFoundException("User not found");
        }
    }
}
