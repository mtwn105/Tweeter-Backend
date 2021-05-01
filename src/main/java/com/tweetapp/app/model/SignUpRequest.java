package com.tweetapp.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String contactNumber;

    private String password;

}
