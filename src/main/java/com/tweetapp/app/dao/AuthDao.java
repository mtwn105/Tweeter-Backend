package com.tweetapp.app.dao;

import com.tweetapp.app.dao.entity.Role;
import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.model.ERole;

import java.util.List;
import java.util.Optional;

public interface AuthDao {

    Optional<User> findByUsername(String username);

    Boolean userExistsByUsername(String username);

    Boolean userExistsByEmail(String email);

    Optional<Role> findRoleByName(ERole roleName);

    User saveUser(User user);

    List<User> getAllUsers();

    List<User> searchUserByUsername(String username);

    Optional<User> findUserById(String userId);
}
