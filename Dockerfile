FROM eclipse-temurin:23

WORKDIR /app

COPY target/contractor-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
