ARG  JAVA_VERSION=8-jdk-alpine
FROM openjdk:${JAVA_VERSION}
LABEL maintainer="giteshdalal@gmail.com"
VOLUME /tmp
ADD ./authservice/build/libs/authservice-1.0.jar app.jar
ADD ./wait-for wait-for
RUN chmod +x wait-for
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap  -XX:MaxRAMFraction=1 -XshowSettings:vm "
ENTRYPOINT [ "sh", "-c", "/wait-for db:3306 -t 20 && /wait-for config:8088 -t 20 && java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
