# Code Challenge - VAGAS.COM - APP Instructions

### Requirements
* Docker
* JDK

#### Setuping project

* Clonning the project
```
git clone https://github.com/vterry/code-challenge.git
```

* Enter the application directory
```
cd app
```
* Run some tests to make sure everything is ok :)
```
./mvnw test
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
* make sure you have daemon exposed - https://docs.docker.com/config/daemon/#troubleshoot-conflicts-between-the-daemonjson-and-startup-scripts
