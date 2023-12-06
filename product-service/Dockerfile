FROM eclipse-temurin:17.0.9_9-jre-focal AS builder
WORKDIR extracted
ADD target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.9_9-jre-focal
WORKDIR app
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
EXPOSE 8084
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]