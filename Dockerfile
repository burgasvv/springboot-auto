FROM bellsoft/liberica-openjdk-debian
COPY target/springboot-auto-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]