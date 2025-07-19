FROM eclipse-temurin:23

WORKDIR /app

COPY target/deal-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
