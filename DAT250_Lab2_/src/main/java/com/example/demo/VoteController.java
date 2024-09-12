package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private PollManager pollManager;

    private List<Vote> votes = new ArrayList<>();

    @PostMapping("/{username}/{pollId}")
    public Vote castVote(@PathVariable String username, @PathVariable String pollId, @RequestBody Vote vote) {
        // Simulate adding a vote for the poll
        votes.add(vote);
        return vote;
    }

    @PutMapping("/{username}/{pollId}")
    public Vote changeVote(@PathVariable String username, @PathVariable String pollId, @RequestBody Vote vote) {
        // Simulate changing the vote for the user on the poll
        votes.removeIf(v -> v.getVoteOption().equals(vote.getVoteOption())); // Remove old vote
        votes.add(vote); // Add new vote
        return vote;
    }

    @GetMapping
    public List<Vote> listVotes() {
        return votes;
    }

    @DeleteMapping
    public void deleteVotes() {
        votes.clear();
    }
}
