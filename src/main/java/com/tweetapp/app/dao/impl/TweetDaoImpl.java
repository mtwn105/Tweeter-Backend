package com.tweetapp.app.dao.impl;

import com.tweetapp.app.dao.TweetDao;
import com.tweetapp.app.dao.entity.Tweet;
import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.dao.repository.TweetRepository;
import com.tweetapp.app.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TweetDaoImpl implements TweetDao {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Tweet> getTweetsByUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> tweetRepository.findByUserIdAndReplyNot(user.getId(), true)).orElse(new ArrayList<>());
    }

    @Override
    public Tweet getTweetById(String id) {
        return tweetRepository.findByTweetId(id);
    }

    @Override
    public Tweet saveOrUpdateTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepository.findByReplyNot(true);
    }

    @Override
    public void deleteTweetById(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("tweetId").is(id)), Tweet.class);
    }
}
