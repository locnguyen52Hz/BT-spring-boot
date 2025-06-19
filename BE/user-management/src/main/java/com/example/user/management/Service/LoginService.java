package com.example.user.management.Service;

import com.example.user.management.Entity.User;
import com.example.user.management.Repository.UserRepository;
import com.example.user.management.Service.Imp.LoginImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginService implements LoginImp {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public boolean checkLogin(String email, String password) {
        User user = userRepository.findUsersByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }


        return password.equals(user.getPassword());
    }


}
