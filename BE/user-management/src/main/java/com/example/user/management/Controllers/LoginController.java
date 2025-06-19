package com.example.user.management.Controllers;


import com.example.user.management.DTO.RequestUserDTO;
import com.example.user.management.DTO.UserDTO;
import com.example.user.management.Service.Imp.LoginImp;
import com.example.user.management.Util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginImp loginImp;

    @Autowired
    JwtHelper jwtHelper;

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody RequestUserDTO userInfo) {
        // Get email and password from request
        String email = userInfo.getEmail();
        String password = userInfo.getPassword();

        // If user is valid, generate new token
        String token = null;
        if(loginImp.checkLogin(email, password)) {
            token = jwtHelper.generateToken(email);
            System.out.println("token login: "+ token);
            System.out.println("check login: " + loginImp.checkLogin(email, password));

            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        else  {
            return new  ResponseEntity<>(token, HttpStatus.UNAUTHORIZED);
        }

    }


}
