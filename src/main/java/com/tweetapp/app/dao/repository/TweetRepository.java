package com.tweetapp.app.dao.repository;

import com.tweetapp.app.dao.entity.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

    List<Tweet> findByUserIdAndReplyNot(String userId, boolean isReply);

    List<Tweet> findByReplyNot(boolean isReply);

    Tweet findByTweetId(String id);

    String deleteByTweetId(String tweetId);

}
