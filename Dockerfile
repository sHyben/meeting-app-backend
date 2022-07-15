FROM openjdk:11 as mysqldoc
EXPOSE 8084
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN chmod 755 /app/mvnw

# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

RUN ./mvnw clean install -Dmaven.test.skip=true

RUN ls -al
ENTRYPOINT ["java","-Xdebug", "-Xrunjdwp:server=y,transport=dt_socket,suspend=n", "-jar","target/meeting-app-backend-0.0.1-SNAPSHOT.jar"]