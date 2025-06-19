package com.example.user.management.Service;

import com.example.user.management.Entity.Roles;
import com.example.user.management.Repository.RolesRepository;
import com.example.user.management.Service.Imp.RolesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService implements RolesImp {

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public List<Roles> getAllRoles() {

        List<Roles> roles = rolesRepository.findAll();

        return roles;
    }
}
