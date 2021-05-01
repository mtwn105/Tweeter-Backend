package com.tweetapp.app.service.impl;

import com.tweetapp.app.dao.AuthDao;
import com.tweetapp.app.dao.TweetDao;
import com.tweetapp.app.dao.entity.Tweet;
import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.service.TweetService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetDao tweetDao;

    @Autowired
    AuthDao authDao;

    @Override
    public Tweet postTweet(String username, Tweet tweet) {
        Optional<User> userOpt = authDao.findByUsername(username);
        return userOpt.map(user -> {
            tweet.setUserId(user.getId());
            tweet.setCreatedDate(new Date());
            return tweetDao.saveOrUpdateTweet(tweet);
        }).orElse(null);
    }

    @Override
    public List<Tweet> getTweetsForUser(String username) {

        Comparator<Tweet> tweetComparator = Comparator.comparing(Tweet::getCreatedDate, Comparator.nullsFirst(Date::compareTo)).reversed();

        return tweetDao.getTweetsByUser(username).stream().sorted(tweetComparator).collect(Collectors.toList());
    }

    @Override
    public List<Tweet> getAllTweets() {

        Comparator<Tweet> tweetComparator = Comparator.comparing(Tweet::getCreatedDate, Comparator.nullsFirst(Date::compareTo)).reversed();

        return tweetDao.getAllTweets().stream().sorted(tweetComparator).collect(Collectors.toList());
    }

    @Override
    public Tweet updateTweet(String username, Tweet tweet, String tweetId) {
        Tweet currentTweet = tweetDao.getTweetById(tweetId);
        currentTweet.setText(tweet.getText());
        return tweetDao.saveOrUpdateTweet(currentTweet);
    }

    @Override
    public void deleteTweet(String username, String tweetId) {
        Tweet tweet = tweetDao.getTweetById(tweetId);
        if (tweet.isReply() && tweet.getOriginalTweetId() != null) {
            Tweet originalTweet = tweetDao.getTweetById(tweet.getOriginalTweetId());
            if (originalTweet != null) {
                originalTweet.getReplies().remove(tweet);
                tweetDao.saveOrUpdateTweet(originalTweet);
            }
        }
        tweetDao.deleteTweetById(tweetId);
    }

    @Override
    public Tweet likeTweet(String username, String tweetId) {
        Optional<User> userOpt = authDao.findByUsername(username);
        if (userOpt.isPresent()) {
            Tweet tweet = tweetDao.getTweetById(tweetId);
            if (CollectionUtils.isNotEmpty(tweet.getUserLikes())) {
                if (tweet.getUserLikes().contains(userOpt.get().getId())) {
                    tweet.getUserLikes().remove(userOpt.get().getId());
                } else {
                    tweet.getUserLikes().add(userOpt.get().getId());
                }
            } else {
                List<String> likes = new ArrayList<>();
                likes.add(userOpt.get().getId());
                tweet.setUserLikes(likes);
            }
            return tweetDao.saveOrUpdateTweet(tweet);
        }
        return null;
    }

    @Override
    public Tweet replyTweet(Tweet tweet, String username, String tweetId) {

        Optional<User> userOpt = authDao.findByUsername(username);
        if (userOpt.isPresent()) {
            Tweet originalTweet = tweetDao.getTweetById(tweetId);
            tweet.setUserId(userOpt.get().getId());
            tweet.setCreatedDate(new Date());
            tweet.setReply(true);
            tweet.setOriginalTweetId(tweetId);
            tweet = tweetDao.saveOrUpdateTweet(tweet);
            if (CollectionUtils.isNotEmpty(originalTweet.getReplies())) {
                originalTweet.getReplies().add(tweet);
            } else {
                List<Tweet> replies = new ArrayList<>();
                replies.add(tweet);
                originalTweet.setReplies(replies);
            }
            tweetDao.saveOrUpdateTweet(originalTweet);
            return tweet;
        }

        return null;
    }
}
