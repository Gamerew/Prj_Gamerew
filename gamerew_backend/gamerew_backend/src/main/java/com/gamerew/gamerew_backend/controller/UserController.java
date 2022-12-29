package com.gamerew.gamerew_backend.controller;


import com.gamerew.gamerew_backend.dto.GlobalResponse;
import com.gamerew.gamerew_backend.messages.ErrorMessage;
import com.gamerew.gamerew_backend.messages.SuccessMessage;
import com.gamerew.gamerew_backend.model.UserModel;
import com.gamerew.gamerew_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    SuccessMessage successMessage;
    ErrorMessage errorMessage;

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> list = userService.getAllUsers();
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<UserModel>> createUser(@RequestBody UserModel newUser){
        GlobalResponse<UserModel> response;
        try{
            UserModel created = userService.createUser(newUser);
            response = new GlobalResponse<>(created, successMessage.USER_CREATION);
        }catch (Exception e){
            response = new GlobalResponse<>(null, errorMessage.USER_CREATION);
        }

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") Long id)
    {
        UserModel entity = userService.getUserById(id);
        return new ResponseEntity<UserModel>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserModel>> updateUser(@RequestBody UserModel user)
    {
        GlobalResponse<UserModel> response;
        try{
            UserModel updated = userService.updateUser(user.getId(), user);
            response = new GlobalResponse<>(updated, successMessage.USER_UPDATE);
        }catch(Exception e){
            response = new GlobalResponse<>(null, errorMessage.USER_UPDATE);
        }
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") Long id)
    {
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserModel>> delete(@PathVariable("id") Long id) {

        GlobalResponse<UserModel> response;
        try {
            UserModel toDelete = userService.getUserById(id);
            userService.deleteUserById(toDelete.getId());
            response = new GlobalResponse<>(toDelete, successMessage.USER_DELETION);
        } catch (Exception e) {
            response = new GlobalResponse<>(null, errorMessage.USER_DELETE);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
