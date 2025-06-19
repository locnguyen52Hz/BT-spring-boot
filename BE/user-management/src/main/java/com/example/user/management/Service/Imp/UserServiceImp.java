package com.example.user.management.Service.Imp;


import com.example.user.management.DTO.UserDTO;
import com.example.user.management.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserServiceImp {

   List<UserDTO>  getAllUsers();

   /**
    * Add user and its role.
    * @param email user's email
    * @param password user's password
    * @param role_id user's roleId
    * @return true if user successfully save, otherwise false
    */
   boolean addUser(UserDTO userDTO);

   boolean deleteUser(int id);

   boolean updateUser(UserDTO userDTO,  MultipartFile fileAvatar);
}
