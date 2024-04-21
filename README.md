# PROBLEM TWO: CONFERENCE TRACK MANAGEMENT

This project is a solution for a big programming conference planning. It helps to fit the received proposals into the time constraints of the day.

## Features

- The conference has multiple tracks each of which has a morning and afternoon session.
- Each session contains multiple talks.
- Morning sessions begin at 9am and must finish by 12 noon, for lunch.
- Afternoon sessions begin at 1pm and must finish in time for the networking event.
- The networking event can start no earlier than 4:00 and no later than 5:00.
- No talk title has numbers in it.
- All talk lengths are either in minutes (not hours) or lightning (5 minutes).
- Presenters will be very punctual; there needs to be no gap between sessions.

# Project Conference Session Track API

This project utilizes OpenAPI for API documentation. The documentation is available on [Swagger UI](https://conferenceapi-c63l.onrender.com/swagger-doc/swagger-ui/index.html#/).

## Deployment on Render using Docker

The project has been deployed on Render using Docker. You can access the deployment at the following link: [Deployment on Render](https://conferenceapi-c63l.onrender.com/).

## Excel Template

An Excel template is included to facilitate the correct functioning of the project. You can find it in the [Excel Template](talk.xlsx) file.


## How to Run

To run the application, you need to have Java 17 installed on your machine. You can run the application using the following command:

```bash
./mvnw spring-boot:run
```

## Sample Input

```
Writing Fast Tests Against Enterprise Rails 60min
Overdoing it in Python 45min
Lua for the Masses 30min
Ruby Errors from Mismatched Gem Versions 45min
Common Ruby Errors 45min
Rails for Python Developers lightning
Communicating Over Distance 60min
Accounting-Driven Development 45min
Woah 30min
Sit Down and Write 30min
Pair Programming vs Noise 45min
Rails Magic 60min
Ruby on Rails: Why We Should Move On 60min
Clojure Ate Scala (on my project) 45min
Programming in the Boondocks of Seattle 30min
Ruby vs. Clojure for Back-End Development 30min
Ruby on Rails Legacy App Maintenance 60min
A World Without HackerNews 30min
User Interface CSS in Rails Apps 30min
```

## Sample Output

```
Track 1:
09:00AM Writing Fast Tests Against Enterprise Rails 60min
10:00AM Overdoing it in Python 45min
10:45AM Lua for the Masses 30min
11:15AM Ruby Errors from Mismatched Gem Versions 45min
12:00PM Lunch
01:00PM Ruby on Rails: Why We Should Move On 60min
02:00PM Common Ruby Errors 45min
02:45PM Pair Programming vs Noise 45min
03:30PM Programming in the Boondocks of Seattle 30min
04:00PM Ruby vs. Clojure for Back-End Development 30min
04:30PM User Interface CSS in Rails Apps 30min
05:00PM Networking Event

Track 2:
09:00AM Communicating Over Distance 60min
10:00AM Rails Magic 60min
11:00AM Woah 30min
11:30AM Sit Down and Write 30min
12:00PM Lunch
01:00PM Accounting-Driven Development 45min
01:45PM Clojure Ate Scala (on my project) 45min
02:30PM A World Without HackerNews 30min
03:00PM Ruby on Rails Legacy App Maintenance 60min
04:00PM Rails for Python Developers lightning
05:00PM Networking Event
```

## Note

The solution may give a different ordering or combination of talks into tracks. This is acceptable; you don’t need to exactly duplicate the sample output given here.

## Explaining the Dockerfile

The `Dockerfile` you provided defines how your Docker image is built. Here's an explanation for each part:

```dockerfile
# Build stage
FROM maven:3.8.1-openjdk-17-slim AS build
```
This line starts the build stage and sets the base image as `maven:3.8.1-openjdk-17-slim`. This image contains Maven and OpenJDK 17, which will be used to build your application.

```dockerfile
WORKDIR /app
```
Sets the working directory in the container to `/app`.

```dockerfile
COPY pom.xml .
RUN mvn dependency:go-offline -B
```
Copies the `pom.xml` file to the container and then runs `mvn dependency:go-offline -B` to download all the dependencies needed to build your application.

```dockerfile
COPY src ./src
RUN mvn package -DskipTests
```
Copies the `src` directory to the container and then runs `mvn package -DskipTests` to build your application without running the tests.

```dockerfile
# Run stage
FROM openjdk:17-alpine
```
Starts a new stage for running your application. The base image is `openjdk:17-alpine`, which contains OpenJDK 17 on an Alpine Linux image, which is a very lightweight Linux distribution.

```dockerfile
EXPOSE 9000
```
Informs Docker that the container will listen on port 9000.

```dockerfile
COPY --from=build /app/target/ConferenceSessionTrackAPI*.jar app.jar
```
Copies the JAR file built in the build stage to the run container. The JAR file is renamed to `app.jar`.

```dockerfile
ENTRYPOINT ["java","-jar","/app.jar"]
```
Defines the command that will be run when the container starts. In this case, it's `java -jar /app.jar`, which starts your application.

In summary, this `Dockerfile` defines two stages: a build stage that builds your application and a run stage that runs your application. This is done to keep the final image as small as possible, as it doesn't include the tools needed to build your application, only the ones needed to run it.