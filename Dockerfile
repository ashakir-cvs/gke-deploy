FROM quay-registry.aetna.com/opensource/gradle:8-jdk17-alpine

COPY build/libs/gke-deploy-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]