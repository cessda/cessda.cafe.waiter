# This image uses the Tomcat server
# Built using Maven 3 and JDK 11

# Compile
FROM maven:3-jdk-17 AS build
WORKDIR /src
COPY pom.xml .
RUN mvn dependency:resolve && mvn dependency:resolve-plugins
COPY . .
RUN mvn verify

# Package
FROM tomcat:10.1-jdk17 AS final
WORKDIR /usr/local/tomcat/webapps
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /src/target/*.war /usr/local/tomcat/webapps/ROOT.war
