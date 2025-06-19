package com.example.user.management.Service;

import com.example.user.management.DTO.UserDTO;
import com.example.user.management.Entity.Roles;
import com.example.user.management.Entity.User;
import com.example.user.management.Repository.RolesRepository;
import com.example.user.management.Repository.UserRepository;
import com.example.user.management.Service.Imp.FileServiceImp;
import com.example.user.management.Service.Imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    FileServiceImp fileServiceImp;

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();


        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setAddress(user.getAddress());
            userDTO.setFullName(user.getFullName());
            userDTO.setAvatar(user.getAvatar());
            userDTO.setEmail(user.getEmail());
            userDTO.setFacebook(user.getFacebook());
            userDTO.setPhone(user.getPhone());
            userDTO.setWebsite(user.getWebsite());
            if (user.getRole() != null) {
                userDTO.setRoleId(user.getRole().getId());
            }
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }


    @Override
    public boolean addUser(UserDTO newUserDTO) {

        try {
            // Get the role from DB
            Optional<Roles> roleOpt = rolesRepository.findById(newUserDTO.getRoleId());
            if (roleOpt.isEmpty()) {
                return false;
            }

            // If role exists, create user and save to DB
            User user = new User();
            user.setEmail(newUserDTO.getEmail());
            user.setPassword(newUserDTO.getPassword());
            user.setRole(roleOpt.get());
            userRepository.save(user);
            return true;

        } catch (Exception e){
            System.out.println( "Exception in addUser " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateUser(UserDTO userDTO, MultipartFile fileAvatar) {
        Optional<User> userOpt = userRepository.findById(userDTO.getId());
        try{
            boolean saveFile = fileServiceImp.saveFile(fileAvatar);
            //kiểm tra user tồn tại và lưu file thành công
            if (userOpt.isPresent() && saveFile) {
                User user = userOpt.get();
                if(userDTO.getFullName() != null) user.setFullName(userDTO.getFullName());

//                if (userDTO.getAvatar() != null) user.setAvatar(fileAvatar.getOriginalFilename());
                if (userDTO.getFacebook() != null) user.setFacebook(userDTO.getFacebook());
                if (userDTO.getPhone() != null) user.setPhone(userDTO.getPhone());
                if (userDTO.getWebsite() != null) user.setWebsite(userDTO.getWebsite());
                if (userDTO.getAddress() != null) user.setAddress(userDTO.getAddress());
                if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());

                if (fileAvatar != null) user.setAvatar(fileAvatar.getOriginalFilename());
                if (fileAvatar == null) user.setAvatar(user.getAvatar());

                if (userDTO.getRoleId() != null) {
                    Optional<Roles> roleOpt = rolesRepository.findById(userDTO.getRoleId());
                    if(roleOpt.isPresent()){
                        Roles role = roleOpt.get();
                        user.setRole(role);
                    }
                }
                userRepository.save(user);
                return true;
            }

        }catch (Exception e){
            System.out.println( "Exception in updateUser " + e.getMessage());
        }
        return false;
    }
}
