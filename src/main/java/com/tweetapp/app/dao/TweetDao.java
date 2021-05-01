package com.tweetapp.app.dao;

import com.tweetapp.app.dao.entity.Tweet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetDao {

    List<Tweet> getTweetsByUser(String username);

    Tweet getTweetById(String id);

    Tweet saveOrUpdateTweet(Tweet tweet);

    List<Tweet> getAllTweets();

    void deleteTweetById(String id);
}
