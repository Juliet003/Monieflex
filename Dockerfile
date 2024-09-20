# Use an official Maven image as the build stage
FROM maven:3.8.7 as build
COPY . .
RUN mvn -B clean package -DskipTests

# Use the official OpenJDK 17 image as the final stage
FROM openjdk:17
COPY --from=build target/*.jar monieflex.monieflex.jar
#WORKDIR /app
#ENV PORT=8081
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "-Dspring.profile.active=${PROFILE}", "monieflex.monieflex.jar"]
