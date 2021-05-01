package com.tweetapp.app.service;

import com.tweetapp.app.dao.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAllUsers();

    List<User> getSearchedUsers(String username);

    User getUserDetails(String userId);
}
