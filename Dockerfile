FROM maven:3
LABEL maintainer="qiaofu"

WORKDIR /opt/lyblog
ADD . /tmp

RUN cd /tmp && mvn package -Pci && mv target/dist/lyblog/* /opt/lyblog/ \
    && rm -rf /tmp/* && rm -rf ~/.m2

EXPOSE 8090

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/lyblog/lyblog-latest.jar","--spring.profiles.active=docker"]
