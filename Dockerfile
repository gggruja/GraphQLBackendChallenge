FROM openjdk:8-jdk-alpine

# Argument is supplied at build time
ARG JAR_FILE

ADD target/${JAR_FILE} /usr/share/app.jar

ENTRYPOINT ["sh", "-c", "/usr/bin/java $JAVA_OPTIONS -jar /usr/share/app.jar"]

EXPOSE 8080