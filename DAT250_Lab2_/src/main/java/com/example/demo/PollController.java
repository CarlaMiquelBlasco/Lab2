package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/{username}")
    public ResponseEntity<Poll> createPoll(@PathVariable String username, @RequestBody Poll poll) {
        // Generate a unique poll ID
        String pollId = UUID.randomUUID().toString();
        poll.setPollId(pollId); // Set pollId in the Poll object

        pollManager.addPoll(pollId, poll); // Store the poll in PollManager

        // Return the poll object, which now contains the pollId, with status CREATED (201)
        return new ResponseEntity<>(poll, HttpStatus.CREATED);
    }

    @GetMapping
    public Collection<Poll> listPolls() {
        return pollManager.getPolls().values();
    }

    @DeleteMapping("/{pollId}")
    public void deletePoll(@PathVariable String pollId) {
        pollManager.getPolls().remove(pollId);
    }
}