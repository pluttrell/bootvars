# Bootvars

This repo is a test project to demonstrate a bug with the `openjdk:8u131-jre-alpine` docker image. With `openjdk:8u121-jre-alpine` you could supply lower case environment variables, however with `openjdk:8u131-jre-alpine` you cannot.

Sending lower case environment variables is required to get Spring Boot to send to the JavaMail special configuration options for SMTP servers that require certain properties. For example GMAIL's SMTP server requires `spring.mail.properties.mail.smtp.starttls.enable=true`.

## How to Demonstrate the Bug

Prerequisites: You'll need Docker, Java 8 and Gradle installed.

Use the two branches: `8u121` and `8u131`. They only differ by the base docker image.

Switch to the `8u121` branch and execute `./gradlew clean dockerTag` and you'll generate an image of `bootvars` tagged with `8u121`.

Then switch to the `8u123` branch and execute `./gradlew clean dockerTag` and you'll generate an image of `bootvars` tagged with `8u123`.
 
### Observe `8u121` Working
Test `8u121` by executing the following. These are fake values and no email will be sent. We're just demonstrating setting environment variables.

	docker run -t -e spring.mail.host=smtp.gmail.com -e spring.mail.port=587 -e spring.mail.username=user@gmail.com -e spring.mail.password=secret -e spring.mail.properties.mail.smtp.auth=true -e spring.mail.properties.mail.smtp.starttls.enable=true bootvars:8u121 

Notice that the output contains the following, which means the environment variables get set properly. This is what we expect to happen.

```
Environment variables visible to SpringBoot:
---------------------------------------------------------------------------------------------
....many omited for brevity...
spring.mail.host=smtp.gmail.com
spring.mail.password=secret
spring.mail.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.username=user@gmail.com
---------------------------------------------------------------------------------------------
SpringBoot's javaMailSender.javaMailProperties:
---------------------------------------------------------------------------------------------
mail.smtp.starttls.enable=true
mail.smtp.auth=true
---------------------------------------------------------------------------------------------
SpringBoot's MailProperties:
---------------------------------------------------------------------------------------------
mail.smtp.starttls.enable=true
mail.smtp.auth=true
---------------------------------------------------------------------------------------------
```

### Observe `8u131` *Not Working*

Test `8u131` by executing:

	docker run -t -e spring.mail.host=smtp.gmail.com -e spring.mail.port=587 -e spring.mail.username=user@gmail.com -e spring.mail.password=secret -e spring.mail.properties.mail.smtp.auth=true -e spring.mail.properties.mail.smtp.starttls.enable=true bootvars:8u131
	
Notice that with `8u131` the lower case environment variables are not visble to the JVM, so SpringBoot's `javaMailSender.javaMailProperties` and `MailProperties` aren't getting set. This is the problem.