package com.tweetapp.app.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String contactNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4)
    @JsonIgnore
    private String password;

    @DBRef
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    public User(String username, String firstName, String lastName, String contactNumber, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
