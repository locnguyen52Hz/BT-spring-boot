package com.example.user.management.Security;

import com.example.user.management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email: "+email);
        com.example.user.management.Entity.User user = userRepository.findUsersByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException(user + " " + "not found");
        }
        return new User(email, user.getPassword(), new ArrayList<>());
    }
}
