FROM openjdk:17-oracle
ENTRYPOINT ["./gradlew", "build"]
EXPOSE 9090
RUN mkdir -p /app/
ADD build/libs/fetchapp-0.0.1-SNAPSHOT.jar /app/fetchapp.jar
ENTRYPOINT ["java", "-jar", "/app/fetchapp.jar"]
