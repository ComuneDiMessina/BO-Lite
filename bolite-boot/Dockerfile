FROM fabric8/java-alpine-openjdk11-jre
LABEL maintainer=almaviva.it
EXPOSE 8181

RUN mkdir -p /impleme-bolite/config
RUN mkdir -p /impleme-bolite/logs
RUN apk --no-cache add msttcorefonts-installer fontconfig && \
    update-ms-fonts && \
    fc-cache -f

ADD target/*.jar impleme-bolite/bolite-boot.jar

WORKDIR /impleme-bolite
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./bolite-boot.jar", ">", "/dev/stdout", "2>&1"]