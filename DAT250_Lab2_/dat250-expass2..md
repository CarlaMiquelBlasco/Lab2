# DAT250 - Experiment Assignment 2: Voting App

## Overview

In this assignment, I developed a simple Voting App using Java and Spring Boot, where users can register, create polls, vote, and manage their polls. The app includes API endpoints to handle these tasks, and I implemented automated tests to ensure the functionality works as expected. Below, I describe the steps I followed, challenges encountered, and how I resolved them.

## Summary of Steps Taken

### Step 0: Get a HTTP Client for Testing
The first step was to download and set up an HTTP client to automate API testing. I opted to use **Bruno** HTTP client for testing the application.

### Step 1: Set up Spring Project Repository
I created a new Spring Boot project, managed via **Gradle**. Additionally, I created a new GitHub public repository set up to organize the code structure, including the domain model, controllers, and tests.

### Step 2: Domain Model
Next, I designed and implemented the **domain model** based on the provided class diagram. This step involved creating simple JavaBeans for `User`, `Poll`, `VoteOption`, and `Vote` with getters and setters.

- **PollManager** was implemented to manage the application's in-memory data using `HashMap` for storing users and polls.
- This model represented the relationships between users, polls, and votes, allowing the application to track which users created polls, cast votes, and which polls were available for voting.

### Step 3: Implement Test Scenarios
Before implementing the API, I prepared the **test scenarios** that would simulate user interactions with the application.

- Created test scenarios that would later verify the behavior of the API, such as creating users, listing polls, casting votes, and deleting polls.
- This involved writing tests to simulate API requests and responses, ensuring the system behaves as expected.

### Step 4: Implement Controller(s)
Once the domain model and test scenarios were set up, I moved on to implementing the actual **API controllers**.

- Defined RESTful API endpoints using `@RestController` and `@...Mapping` annotations to manage users, polls, and votes.
- Implemented handlers for key operations: user creation, poll management, voting, and deleting polls.
- Each API endpoint was mapped to a URL and HTTP method (e.g., `POST /users`, `GET /polls`, `POST /votes`).
- JSON serialization and deserialization were handled automatically by Spring to exchange data between the client and server.

### Step 5: Automate Testing
The final step was to write **automated tests** that could verify the behavior of the application without manual interaction.

- Automated tests using **RestTemplate** were written to simulate API requests programmatically.
- These tests covered all key scenarios: creating users, creating polls, voting, changing votes, listing users/polls/votes, and deleting polls.
- Used `assertEquals`, `assertNotNull`, and other assertions to validate the responses from the API, ensuring that the application behaved as expected.
- Tests were run via **Gradle** to automate and check the correctness of the application at every step.


## Technical Problems Encountered

### 1. **Setting Up Spring Boot**
Initially, I encountered a few technical issues while setting up Spring Boot with Gradle. Specifically, I had difficulty configuring the `build.gradle` file to include the necessary dependencies for both Spring Boot and the testing framework. I resolved this by carefully reading the Spring Boot documentation and ensuring the correct versions of `spring-boot-starter-web` and `spring-boot-starter-test` were included in the `build.gradle` file.

**Solution:** Followed Spring documentation and updated the Gradle file to include correct dependencies for `spring-boot-starter-test`, `junit-jupiter`, and `spring-boot-starter-web`.

### 2. **Understanding the Poll Identifier**
I struggled with how to uniquely identify polls when users create or interact with them. Initially, I thought the poll could be identified using the question or username, but I realized that using a `PollID` would make identification easier and unique. The issue was that I needed a way to generate unique `PollID`s dynamically. Eventually, I used `UUID` to generate a unique identifier for each poll when created.

**Solution:** Used `UUID` for generating unique Poll IDs in the `PollManager`.

### 3. **Executing Tests Before Setting Up Controllers**
One of the main challenges I faced was during the third step of the assignment, where I was trying to set up and execute tests before the API endpoints (handlers) were implemented. I was confused about how the testing should work and thought the tests were supposed to be executed at that stage (while they were only supposed to be prepared). After discussing with my peers, I realized the tests could not run until I had properly set up the `@RestController` and associated handlers.

**Solution:** First, I needed to complete the handlers using `@RestController` and `@...Mapping` annotations, then proceed to execute the tests.

### 4. **Automating the Tests**
Automating the tests was initially confusing. I was unsure how to correctly simulate requests and validate responses using Java. After researching `RestTemplate` and understanding how it works for making HTTP requests in a test environment, I was able to write the test cases to create users, create polls, vote on them, and ensure that the automated tests reflected the expected behavior.

**Solution:** Used Spring's `RestTemplate` to simulate API requests in the test environment and verify the behavior of the Voting App. This allowed me to automate tests that cover user creation, poll creation, voting, changing votes, and deleting polls.

## Pending Issues and Challenges

1. **Handling Error Responses:**
   I did not fully implement error handling for invalid actions (e.g., attempting to vote on a non-existent poll or voting after the deadline). This is an area that could be improved by adding better validation and response handling.

2. **Concurrency and Data Persistence:**
   The current implementation uses in-memory storage (`HashMap`), which means the data is lost when the application restarts. A more robust solution would use a persistent database and handle potential concurrency issues when multiple users try to vote or create polls simultaneously.

3. **Further Refining the Automated Tests:**
   The automated tests could be expanded to cover more edge cases, such as trying to vote after the poll deadline or ensuring that private polls behave correctly. Currently, the focus was on ensuring the basic functionality works.

## Conclusion

This assignment provided valuable experience in building and testing a REST API using Spring Boot. While I faced some challenges during the implementation, particularly with setting up the API endpoints and automating tests, I was able to resolve them by consulting documentation and exploring Spring Boot's features. I learned about handling user input, managing polls, and testing API-driven applications programmatically.


