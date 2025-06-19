package com.example.user.management.Controllers;


import com.example.user.management.Entity.Roles;
import com.example.user.management.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RolesRepository rolesRepository;

    @GetMapping("")
    public ResponseEntity<List<Roles>> getAllRoles() {
        return new ResponseEntity<>(rolesRepository.findAll(), HttpStatus.OK);
    }

}
