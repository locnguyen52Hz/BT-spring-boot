package com.example.user.management.Controllers;

import com.example.user.management.DTO.RequestUserDTO;
import com.example.user.management.DTO.UserDTO;

import com.example.user.management.Entity.User;
import com.example.user.management.Service.Imp.FileServiceImp;
import com.example.user.management.Service.Imp.UserServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    private FileServiceImp fileServiceImp;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return new ResponseEntity<>(userServiceImp.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {

        if (userDTO.getRoleId() == null) {
            return new ResponseEntity<>("Ko đc để id trống",HttpStatus.BAD_REQUEST);
        }

        boolean result = userServiceImp.addUser(userDTO);

        return new ResponseEntity<>( result,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean result = userServiceImp.deleteUser(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestPart("user") UserDTO userDTO, @RequestPart(value = "avatar", required = false) MultipartFile fileAvatar) {
        boolean updateSuccess = userServiceImp.updateUser(userDTO, fileAvatar);

        if (updateSuccess) {
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }
        else  {
            return new ResponseEntity<>(userDTO,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/avatar/{filename:.+}")
    public ResponseEntity<?> getUserAvatar(@PathVariable String filename) {

        Resource resource = fileServiceImp.loadFileAsResource(filename);
        if (resource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
