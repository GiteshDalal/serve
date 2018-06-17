ARG  JAVA_VERSION=8-jdk-alpine
FROM openjdk:${JAVA_VERSION}
LABEL maintainer="giteshdalal@gmail.com"
VOLUME /tmp
ADD build/libs/authservice-1.0.jar app.jar
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap  -XX:MaxRAMFraction=1 -XshowSettings:vm "
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 8090
