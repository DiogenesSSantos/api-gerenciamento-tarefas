FROM eclipse-temurin:21-jdk
ARG APP_PROFILE=prod
ENV SPRING_PROFILES_ACTIVE=${APP_PROFILE}
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]