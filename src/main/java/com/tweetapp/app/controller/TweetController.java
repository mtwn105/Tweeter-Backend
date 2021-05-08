package com.tweetapp.app.controller;

import com.tweetapp.app.dao.entity.Tweet;
import com.tweetapp.app.service.TweetService;
import com.tweetapp.app.service.impl.MessageProducerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

    @Autowired
    TweetService tweetService;

    @Autowired
    MessageProducerService kafkaProducer;

    @GetMapping()
    public ResponseEntity<List<Tweet>> getAllTweets() {

        List<Tweet> response = tweetService.getAllTweets();

        if (CollectionUtils.isNotEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/{username}/add")
    public ResponseEntity<Tweet> postTweet(@Valid @RequestBody Tweet tweet, @PathVariable String username) {

        Tweet response = tweetService.postTweet(username, tweet);

        if (response != null) {
            // Send tweet to kafka topic
            kafkaProducer.sendTweet(tweet);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Tweet>> getTweetsForUser(@PathVariable String username) {

        List<Tweet> response = tweetService.getTweetsForUser(username);

        if (CollectionUtils.isNotEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{username}/update/{tweetId}")
    public ResponseEntity<Tweet> updateTweet(
            @Valid @RequestBody Tweet tweet,
            @PathVariable String username,
            @PathVariable String tweetId
    ) {

        Tweet response = tweetService.updateTweet(username, tweet, tweetId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{username}/delete/{tweetId}")
    public ResponseEntity<String> deleteTweet(
            @PathVariable String username,
            @PathVariable String tweetId
    ) {

        try {
            tweetService.deleteTweet(username, tweetId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}/like/{tweetId}")
    public ResponseEntity<Tweet> likeTweet(
            @PathVariable String username,
            @PathVariable String tweetId
    ) {

        Tweet response = tweetService.likeTweet(username, tweetId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{username}/reply/{tweetId}")
    public ResponseEntity<Tweet> replyTweet(
            @Valid @RequestBody Tweet tweet,
            @PathVariable String username,
            @PathVariable String tweetId
    ) {

        Tweet response = tweetService.replyTweet(tweet, username, tweetId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
