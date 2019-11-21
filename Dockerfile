# This image uses the Tomcat server
FROM tomcat:9.0-jdk11 AS base
WORKDIR /usr/local/tomcat/webapps
EXPOSE 8080

# Built using Maven 3 and JDK 11
FROM maven:3-jdk-11 AS build
WORKDIR /src
COPY . .
RUN mvn package

FROM base AS final
RUN rm -r /usr/local/tomcat/webapps/*
COPY --from=build /src/target/*.war /usr/local/tomcat/webapps/ROOT.war
ENTRYPOINT ["catalina.sh", "run"]