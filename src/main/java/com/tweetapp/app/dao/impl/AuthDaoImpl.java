package com.tweetapp.app.dao.impl;

import com.tweetapp.app.dao.AuthDao;
import com.tweetapp.app.dao.entity.Role;
import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.dao.repository.RoleRepository;
import com.tweetapp.app.dao.repository.UserRepository;
import com.tweetapp.app.model.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthDaoImpl implements AuthDao {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<Role> findRoleByName(ERole roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchUserByUsername(String username) {
        return userRepository.findByUsernameRegex(username);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }
}
