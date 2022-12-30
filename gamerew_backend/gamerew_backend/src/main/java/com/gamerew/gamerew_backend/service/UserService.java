package com.gamerew.gamerew_backend.service;

import com.gamerew.gamerew_backend.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserModel> getAllUsers();
    UserModel getUserById(Long id);
    UserModel updateUser(Long userId, UserModel user);
    void deleteUserById(Long id);
    UserModel createUser(UserModel newUser);
}
