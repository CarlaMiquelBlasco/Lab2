package com.example.demo;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class PollManager {
    private Map<String, User> users;
    private Map<String, Poll> polls;
    private Map<String, List<Vote>> pollVotes;

    public PollManager() {
        this.users = new HashMap<>();
        this.polls = new HashMap<>();
        this.pollVotes = new HashMap<>();
    }

    public void addUser(String username, User user) {
        users.put(username, user);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addPoll(String pollId, Poll poll) {
        polls.put(pollId, poll);
        pollVotes.put(pollId, new ArrayList<>()); // Initialize vote list for this poll
    }

    public Poll getPoll(String pollId) {
        return polls.get(pollId);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Poll> getPolls() {
        return polls;
    }

    public List<Vote> getVotesForPoll(String pollId) {
        return pollVotes.get(pollId);
    }

    public void addVote(String pollId, Vote vote) {
        pollVotes.get(pollId).add(vote);
    }

    public void updateVote(String pollId, Vote vote) {
        List<Vote> votes = pollVotes.get(pollId);
        votes.removeIf(v -> v.getVoteOption().equals(vote.getVoteOption())); // Remove old vote
        votes.add(vote);
    }

    public void deletePoll(String pollId) {
        polls.remove(pollId);
        pollVotes.remove(pollId);
    }
}