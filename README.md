# spring-boot-oauth2
A sample user-password authentication login form together with OAuth2.0 of `Github` and `@Line` using spring-boot-security.

## Environment
* JDK 1.8
* Apache Maven 3.6.x

## Getting Started
1. Download the project or using(`git clone https://github.com/er-ri/spring-boot-auth.git`).
2. Replace `<client id>` and `<client secrety>` with yours OAuth app info in `src/main/resources/application.properties`.
```
# OAuth2.0 for github
spring.security.oauth2.client.registration.github.client-id=<github client id>
spring.security.oauth2.client.registration.github.client-secret=<github client secret>
...
# OAuth2.0 for line
spring.security.oauth2.client.registration.line.client-id=<line channel id>
spring.security.oauth2.client.registration.line.client-secret=<line channel secret>
```
* Set the `callback URL` to `http://localhost:8080/login/oauth2/code/github` for `Github` and `http://localhost:8080/login/oauth2/code/line` for `@Line`, respectively.
3. Go to the project root folder(`cd spring-boot-oauth2`).
4. Build the project(`mvn package`).
5. Run the Jar file in target folder.
6. Open the Browser and navigate to `http://localhost:8080/`
7. Login with "`testuser`" and "`password`" or your `Github` and `@Line` account.

## Using LINE Messaging API(Optional)
```
line.oauth2.channel-id=<line channel id>
line.oauth2.channel-secret=<line channel secret>
line.oauth2.channel-token=<line channel token>
```

## Technology Stack
| Technology               | Name            |
|--------------------------|-----------------|
| Web Framework            | Spring Boot     |
| Authentication Framework | Spring Security |
| Template Engine          | Thymeleaf       |
| Database                 | H2              |
| Database Mappting Tool   | Hibernate       |
