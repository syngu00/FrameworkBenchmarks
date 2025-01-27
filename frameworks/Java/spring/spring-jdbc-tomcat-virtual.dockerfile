FROM maven:3-eclipse-temurin-19 as maven

RUN mvn -version
WORKDIR /spring


COPY hello-spring-app hello-spring-app
COPY hello-spring-interface hello-spring-interface
COPY hello-spring-jdbc hello-spring-jdbc
COPY hello-spring-jpa hello-spring-jpa
COPY hello-spring-mongo hello-spring-mongo
COPY hello-spring-tomcat hello-spring-tomcat

COPY pom.xml pom.xml

RUN mvn package -q -P spring-jdbc,spring-tomcat

FROM eclipse-temurin:19-jre-focal

WORKDIR /spring
COPY --from=maven /spring/hello-spring-app/target/hello-spring-app-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "--enable-preview", "-XX:+UseNUMA", "-XX:+UseG1GC", "-XX:+DisableExplicitGC", "-XX:+UseStringDeduplication", "-Dlogging.level.root=OFF", "-jar", "app.jar", "--spring.profiles.active=jdbc,virtual"]
