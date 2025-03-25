# This image uses the Tomcat server
# Built using Maven 3 and JDK 11

# Using Java 21
FROM eclipse-temurin:21 AS build
WORKDIR /src

# Copy in the Maven Wrapper
COPY mvnw .
COPY .mvn .mvn

# Resovle dependencies
COPY pom.xml .
RUN ./mvnw dependency:resolve && ./mvnw dependency:resolve-plugins

# Compile Waiter
COPY . .
RUN ./mvnw verify

# Package
FROM eclipse-temurin:21 AS final
WORKDIR /opt/cessda/waiter
COPY --from=build /src/target/*.jar /opt/cessda/waiter/waiter.jar

ENTRYPOINT ["java", "-jar", "waiter.jar"]
