FROM openjdk:17-alpine

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

EXPOSE 9000

CMD ["java", "-jar", "target/ConferenceSessionTrackAPI*.jar"]