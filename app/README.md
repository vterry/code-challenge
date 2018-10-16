# Code Challenge - VAGAS.COM - APP

## The Challenge
Our client have requested us to build a small application based on the requirements at: https://static.vagas.com.br/RH/desafio-tecnico.pdf

## The solution: 
We have decided to build a small and simplest Microservice Application based on SpringBoot and Docker. SpringBoot provides some tools that can help us to build a clear and scalable Rest Apis very easy. With SpringBoot we can, if needed, aggregate new funcionalites very easy, or transform this into a SpringClound Application to enjoy some Netflix OSS's tools as well. 

To keep this application simple to maintain, we have decided to "dockerize" it into a container that enables us deploy or run it very quickly and simple.

## Extra documentation:
To provide a straightful comprehension about our apis, we also provide a SWAGGER documentation, to keep it clear and visual: http://localhost:9000/swagger-ui.html 

## Setuping your environment

### Requirements
* Git
* Docker
* JDK

#### Download the project

* Clonning the project
```
git clone https://github.com/vterry/code-challenge.git
```

* Enter the application directory
```
cd app
```

#### Running APP from DOCKER

```
./mvnw dockerfile:build
docker run -d -p 9000:9000 spring-boot-app
```

#### Running APP from MAVEN

```
./mvn spring-boot:run
```

#### NOTES:
* Make sure you have Docker Daemon (tcp://localhost:2375) exposed - https://docs.docker.com/config/daemon/#troubleshoot-conflicts-between-the-daemonjson-and-startup-scripts
