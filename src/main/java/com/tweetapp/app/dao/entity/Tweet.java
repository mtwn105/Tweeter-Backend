package com.tweetapp.app.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "tweets")
public class Tweet{

    @Id
    private String tweetId;

    private String userId;

    @Size(max = 144, message = "Tweet cannot exceed 144 characters limit")
    private String text;

    private List<String> userLikes;

    private boolean reply;

    private String originalTweetId;

    @DBRef
    private List<Tweet> replies;

    @CreatedDate
    private Date createdDate;

}
