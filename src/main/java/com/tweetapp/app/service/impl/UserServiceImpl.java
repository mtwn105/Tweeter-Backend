package com.tweetapp.app.service.impl;

import com.tweetapp.app.dao.AuthDao;
import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AuthDao authDao;

    @Override
    public List<User> getAllUsers() {
        return authDao.getAllUsers();
    }

    @Override
    public List<User> getSearchedUsers(String username) {
        return authDao.searchUserByUsername(username);
    }

    @Override
    public User getUserDetails(String userId) {
        return authDao.findUserById(userId).orElse(null);
    }
}
