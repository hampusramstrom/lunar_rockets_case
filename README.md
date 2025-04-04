# 🪐 Backend Engineer Challenge: Rockets 🚀

## Author
- **Name:** Hampus Ramström
- **Email:** hampusram93@gmail.com

## The Challenge
Can be found in the [CHALLENGE.md](CHALLENGE.md).

## The Code Language
I followed the instructions and picked a language that I feel comfortable in, where in this case I went for Java as I'm used to work with the [Guice](https://github.com/google/guice) dependency injection framework.

## Prerequisites
- Java 21 installed (developed with 21.0.6, can most probably use earlier versions).
- Gradle 8 installed (developed with 8.13, probably works with earlier version).
- An IDE supporting Java.

## Running The Solution
Compile and run the code in your IDE of choice (main class can be found in [App.java](app/src/main/java/org/rockets/App.java)).

Run the `rockets` executable as (note `http`):
```bash
./rockets launch "http://localhost:8088/messages" --message-delay=500ms --concurrency-level=1
```

## Questions and Remarks
- A message number was described as: `Apart from the channel each message also contains a message number which expresses the order of the message within a channel.`
  
  But how does the message number of a `MissionChanged` message fit into this? As it can relate to numerous channels, where I see it a bit improbable that the message number aligns over all the channels (that is being the next in line).
- In the provided `README.md` (now referenced as [CHALLENGE.md](CHALLENGE.md)), a bash example on how to run the test program is given. However, `--message-delay` flag should be used instead of a `--delay` flag.

## Assumptions
- That it's ok to run the solution with a `http` protocol (as we're running it locally). This is to avoid having to configure a self-signed certificate for enabling `https`.
- Our service is the only one communicating with the rockets and updating the repository, which enables having a [IRocketStates.java](/app/src/main/java/org/rockets/state/IRocketStates.java) state (in-memory in our case) to avoid calling the repository and compute the state each time.
- I assume that all messages have correct values (beyond the risk of messages arriving out of order and the at-least-once guarantee).
- I assume that messages always will be resent on error responses from the `/messages` endpoint.
- I assume that there will be no gaps in the message number sequence, that is all numbers in the sequence will be eventually received.

## Improvements
- When looking at the output when running the `rockets` executable, it _seems_ like my assumption regarding the message number sequence doesn't hold. In that case, my solution would need to be redesigned depending on what can be expected.
- Having a functionality to ingest all messages in the repository on bootup and set the state. Not relevant in my case when relying on an in-memory repository that disappears when shutting the application down (compared to having a database repository).
- If efficiency of ingesting all the messages becomes a problem, a more powerful setup could be considered. An example of this would be to a solution more leaning towards a solution with an event-driven architecture. With a broker between the producer (the `rockets` executable) and the `consumers` (in our case my solution). Then we could increase the number of message consumers to ramp up the ingestion and scale horizontally.
- I have tried to add some different types of tests, but haven't covered everything that could be relevant due to time constraints.
- I added very little Javadoc, also due to time constraints. If I would add it, then it would most probably be to the different interface methods.
- There are some TODOs still in the code.
