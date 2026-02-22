FROM eclipse-temurin:11-jdk AS build

COPY . /etc/headless/

WORKDIR /etc/headless/org.pcm.headless.rest
RUN ../gradlew clean bootJar

WORKDIR ../org.pcm.headless.agent
RUN ../gradlew clean build jar

FROM eclipse-temurin:11-jdk

ADD ./start.sh /start.sh
RUN chmod +x /*.sh

WORKDIR /app

COPY --from=build /etc/headless/org.pcm.headless.rest/build/libs /app/
COPY --from=build /etc/headless/org.pcm.headless.agent/build/libs /app/agent/

EXPOSE 8080 8080

ENTRYPOINT ["/start.sh"] 
CMD []