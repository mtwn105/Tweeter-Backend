package com.tweetapp.app.dao.repository;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tweetapp.app.dao.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "{'username': {$regex : ?0, $options: 'i'}}")
    List<User> findByUsernameRegex(String username);

}
