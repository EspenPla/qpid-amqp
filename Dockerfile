FROM openjdk:8-jre-alpine
COPY ./target/amqp-0.0.1-SNAPSHOT.jar /opt/amqp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["/bin/sh", "-c" , "echo 35.228.223.206 interchange-test.nordic-way.io >> /etc/hosts && exec java -jar /opt/amqp-0.0.1-SNAPSHOT.jar" ]

EXPOSE 8080:8080
