FROM docker.io/library/maven:3.9.8 as build

RUN apt update && apt install wget -y
RUN mkdir -p ~/.postgresql
RUN wget "https://storage.yandexcloud.net/cloud-certs/CA.pem" > ~/.postgresql/root.crt
RUN chmod 0600 ~/.postgresql/root.crt

ARG DB_URL
ARG DB_PASSWORD
ARG DB_USERNAME

COPY back/src /usr/src/app/src
COPY back/pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:17-alpine
COPY --from=build /usr/src/app/target/*.jar /application.jar
#COPY --from=build /usr/src/app/target/classes/img /img
#COPY --from=build /usr/src/app/target/classes/fonts /fonts
#COPY --from=build /usr/src/app/target/classes/docs /docs
WORKDIR .

EXPOSE 8080
CMD ["java","-jar","/application.jar"]