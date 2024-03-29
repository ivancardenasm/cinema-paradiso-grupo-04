# Cinema Paradiso App


ISPP Group 4

## Codacy Coverage Reporter

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7c222eeb6ee742f79dc88d94e4331e7e)](https://www.codacy.com/gh/ivan-desing-testing/CinemaParadisoGrupo-04/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ivan-desing-testing/CinemaParadisoGrupo-04&amp;utm_campaign=Badge_Grade)

## Travis Status

Master:

[![Build Status](https://travis-ci.com/ivan-desing-testing/CinemaParadisoGrupo-04.svg?token=eRyUu1vNYWXwcHGXDak4&branch=master)](https://travis-ci.com/ivan-desing-testing/CinemaParadisoGrupo-04)

Develop:

[![Build Status](https://travis-ci.com/ivan-desing-testing/CinemaParadisoGrupo-04.svg?token=eRyUu1vNYWXwcHGXDak4&branch=develop)](https://travis-ci.com/ivan-desing-testing/CinemaParadisoGrupo-04)
## Running cinemaparadiso locally
Cinema Paradiso is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/Pabcalper/CinemaParadisoGrupo-04.git
cd cinemaparadiso
./mvnw package
java -jar target/*.jar
```

You can then access cinema paradiso here: http://localhost:9090/

Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```

## Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0-M2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.0-M2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-developing-web-applications)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)