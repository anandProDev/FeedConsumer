FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/FeedConsumer-1.0-SNAPSHOT.jar feedConsumer.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /feedConsumer.jar
EXPOSE 8080