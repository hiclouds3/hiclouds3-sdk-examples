FROM maven:3-openjdk-8-slim

RUN apt update && \
  apt install -y curl && \
  apt clean all

COPY entrypoint.sh /opt/
COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]
RUN cd /opt/sample/JAVA_SDK_Sample/ && \
  mvn clean package

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
