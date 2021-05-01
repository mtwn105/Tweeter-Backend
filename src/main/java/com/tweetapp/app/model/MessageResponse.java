package com.tweetapp.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class MessageResponse {

    private String message;

    private HttpStatus httpStatus;

}
