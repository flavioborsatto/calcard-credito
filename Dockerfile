FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/calcard-credito-*.war app.war
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.war"]
