FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/airportdemo-1.0.war app.war
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]
