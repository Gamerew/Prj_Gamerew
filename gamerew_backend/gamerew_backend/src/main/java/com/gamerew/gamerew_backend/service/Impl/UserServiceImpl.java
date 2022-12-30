package com.gamerew.gamerew_backend.service.Impl;

import com.gamerew.gamerew_backend.model.UserModel;
import com.gamerew.gamerew_backend.repository.UserRepository;
import com.gamerew.gamerew_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = true)
    UserRepository repository;

    @Override
    public List<UserModel> getAllUsers()
    {
        List<UserModel> userList = repository.findAll();
        if(userList.size() > 0) {
            return userList;
        } else {
            return new ArrayList<UserModel>();
        }
    }

    @Override
    public UserModel getUserById(Long id)
    {
        Optional<UserModel> user = repository.findById(id);
        return user.get();
    }

    @Override
    public UserModel updateUser(Long userId, UserModel newUser)
    {
        Optional<UserModel> user = repository.findById(userId);

        if(user.isPresent())
        {
            UserModel newEntity = user.get();
            newEntity.setFullname(newUser.getFullname());
            newEntity.setEmail(newUser.getEmail());
            newEntity.setRole(newUser.getRole());
            repository.save(newEntity);
            return newEntity;
        } else {
            return null; //Exception should be added
        }
    }

    @Override
    public void deleteUserById(Long id)
    {
        Optional<UserModel> user = repository.findById(id);
        if(user.isPresent()){
            repository.deleteById(user.get().getId());
        }
    }

    @Override
    public UserModel createUser(UserModel newUser) {
        return repository.save(newUser);
    }
}