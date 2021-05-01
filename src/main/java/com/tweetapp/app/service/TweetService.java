package com.tweetapp.app.service;

import com.tweetapp.app.dao.entity.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TweetService {
    Tweet postTweet(String username, Tweet tweet);

    List<Tweet> getTweetsForUser(String username);

    List<Tweet> getAllTweets();

    Tweet updateTweet(String username, Tweet tweet, String tweetId);

    void deleteTweet(String username, String tweetId);

    Tweet likeTweet(String username, String tweetId);

    Tweet replyTweet(Tweet tweet, String username, String tweetId);
}
