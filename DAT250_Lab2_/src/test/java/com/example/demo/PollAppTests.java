package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollAppTests {

	private final RestTemplate restTemplate = new RestTemplate();
	private final String baseUrl = "http://localhost:8080";

	@Test
	public void testUserCreationAndListing() {
		// Step 1: Create first user using no-argument constructor
		User user1 = new User();
		user1.setUsername("user1");
		user1.setEmail("user1@example.com");
		ResponseEntity<User> response1 = restTemplate.postForEntity(baseUrl + "/users", user1, User.class);
		assertEquals(200, response1.getStatusCodeValue());
		assertEquals("user1", response1.getBody().getUsername());

		// Step 2: List users
		ResponseEntity<User[]> usersResponse = restTemplate.getForEntity(baseUrl + "/users", User[].class);
		List<User> users = Arrays.asList(usersResponse.getBody());
		assertEquals(1, users.size());

		// Step 3: Create second user using no-argument constructor
		User user2 = new User();
		user2.setUsername("user2");
		user2.setEmail("user2@example.com");
		ResponseEntity<User> response2 = restTemplate.postForEntity(baseUrl + "/users", user2, User.class);
		assertEquals(200, response2.getStatusCodeValue());
		assertEquals("user2", response2.getBody().getUsername());

		// Step 4: List users again
		usersResponse = restTemplate.getForEntity(baseUrl + "/users", User[].class);
		users = Arrays.asList(usersResponse.getBody());
		assertEquals(2, users.size());
	}

	@Test
	public void testPollCreationAndVote() {
		// Create a new user (User1) using no-argument constructor
		User user1 = new User();
		user1.setUsername("user1");
		user1.setEmail("user1@example.com");
		restTemplate.postForEntity(baseUrl + "/users", user1, User.class);

		// Step 1: User1 creates a new poll
		Poll poll = new Poll();
		poll.setQuestion("What's your favorite color?");
		poll.setPublishedAt(Instant.parse("2024-09-05T12:00:00Z")); // Convert String to Instant
		poll.setValidUntil(Instant.parse("2024-09-06T12:00:00Z")); // Convert String to Instant

		// Create VoteOptions using no-argument constructor and setters
		VoteOption option1 = new VoteOption();
		option1.setCaption("Red");
		option1.setPresentationOrder(1);

		VoteOption option2 = new VoteOption();
		option2.setCaption("Blue");
		option2.setPresentationOrder(2);

		poll.setVoteOptions(Arrays.asList(option1, option2));

		ResponseEntity<Poll> pollResponse = restTemplate.postForEntity(baseUrl + "/polls/user1", poll, Poll.class);
		assertEquals(201, pollResponse.getStatusCodeValue());
		Poll createdPoll = pollResponse.getBody();
		assertNotNull(createdPoll);

		// Step 2: List polls
		ResponseEntity<Poll[]> pollsResponse = restTemplate.getForEntity(baseUrl + "/polls", Poll[].class);
		List<Poll> polls = Arrays.asList(pollsResponse.getBody());
		assertEquals(1, polls.size());

		// Step 3: User2 votes on the poll
		Vote vote = new Vote();
		vote.setPublishedAt(Instant.parse("2024-09-05T13:00:00Z")); // Convert String to Instant

		// Create a VoteOption using no-argument constructor and setters
		VoteOption voteOption = new VoteOption();
		voteOption.setCaption("Blue");
		voteOption.setPresentationOrder(2);

		vote.setVoteOption(voteOption);
		restTemplate.postForEntity(baseUrl + "/votes/user2/" + createdPoll.getPollId(), vote, Vote.class);

		// Step 4: User2 changes the vote
		Vote updatedVote = new Vote();
		updatedVote.setPublishedAt(Instant.parse("2024-09-05T14:00:00Z")); // Convert String to Instant

		VoteOption updatedVoteOption = new VoteOption();
		updatedVoteOption.setCaption("Red");
		updatedVoteOption.setPresentationOrder(1);

		updatedVote.setVoteOption(updatedVoteOption);
		restTemplate.exchange(baseUrl + "/votes/user2/" + createdPoll.getPollId(), HttpMethod.PUT, new HttpEntity<>(updatedVote), Vote.class);

		// Step 5: List votes (should show the updated vote for user2)
		ResponseEntity<Vote[]> votesResponse = restTemplate.getForEntity(baseUrl + "/votes", Vote[].class);
		List<Vote> votes = Arrays.asList(votesResponse.getBody());
		assertEquals(2, votes.size());
		assertEquals("Blue", votes.get(0).getVoteOption().getCaption());

		// Step 6: Delete the poll
		restTemplate.delete(baseUrl + "/polls/" + createdPoll.getPollId());

		// Step 7: List votes again (should be empty since the poll is deleted)
		votesResponse = restTemplate.getForEntity(baseUrl + "/votes", Vote[].class);
		votes = Arrays.asList(votesResponse.getBody());
		assertEquals(0, votes.size());
	}



}


/*SETP 4 FALLA: EL VOTE NO S'ACTUALITZA BE!!
* STEP 6 TAMBE FALLA: NO S'ELIMINEN LES POLLS.*/