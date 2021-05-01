package com.tweetapp.app.dao.repository;

import com.tweetapp.app.dao.entity.Role;
import com.tweetapp.app.model.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(ERole roleName);

}
