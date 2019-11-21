# CESSDA Café: Waiter

[![Build Status](https://jenkins-dev.cessda.eu/buildStatus/icon?job=cessda.cafe.waiter%2Fmaster)](https://jenkins-dev.cessda.eu/job/cessda.cafe.waiter/job/master/)
[![Quality Gate Status](https://sonarqube-dev.cessda.eu/component_measures?id=eu.cessda.cafe%3Awaiter&metric=alert_status)](https://sonarqube-dev.cessda.eu/dashboard?id=eu.cessda.cafe%3Awaiter)

This project is an implementation of the CESSDA Café Waiter program using Java API for RESTful Web Services [JAX-RS 2.0](https://download.oracle.com/otndocs/jcp/jaxrs-2_0-fr-eval-spec/index.html) Specifications deploying [Jersey](https://eclipse-ee4j.github.io/jersey/) as the Interface implementation.   

## Prerequisites

The following tools need to be installed locally to build and test the application:

* Java JDK 11
* Maven 

## Getting Started

To compile this application, run:

```bash
mvn install 
```

This will create a WAR suitable for deploying on Java Application servers (e.g. Tomcat, Jetty...).

To compile and run this application locally run:

```bash
mvn jetty:run
```

The application will run locally at <http://localhost:8080/>.

## Docker

To create a Docker image, run:

```bash
mvn install jib:dockerBuild -Dimage=IMAGE_TAG
```

Substitute `IMAGE_TAG` for a desired image tag. The application can then be run using `docker run -p 8080:8080 IMAGE_TAG` and will be hosted on <http://localhost:8080/>.

## Options

To set url environment variable for waiter component:

```bash
export WAITER_URL="http://localhost:1338"
```

## Technology Stack

Several frameworks are used in this application:

| Framework/Technology									| Description													|
| ----------------------------------------------------- | ------------------------------------------------------------- |
| [JAX-RS](https://jax-rs.github.io/apidocs/2.0/)		| Java REST API Specifications Framework                     	|
| [Jersey](https://eclipse-ee4j.github.io/jersey/)  	| Java Implementation for JAX-RS                    			|
| [JDK 11](https://jdk.java.net/11/)		        	| Java Platform for J2EE (Enterprise Edition)					|

## Resources

[Issue Tracker](https://bitbucket.org/cessda/cessda.cafe.waiter/issues?status=new&status=open)

## Authors

**Joshua Tetteh Ocansey (joshua.ocansey@cessda.eu )** - *CESSDA Technical Officer*

**Matthew Morris (matthew.morris@cessda.eu)** - *CESSDA Technical Officer*