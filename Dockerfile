FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/ifoodtest-0.0.1-SNAPSHOT.jar /app.jar
ADD wait-for-it.sh /wait-for-it.sh
ENV JAVA_OPTS=""
ENTRYPOINT  ./wait-for-it.sh
