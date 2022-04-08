FROM ruby:2.7-slim

RUN apt update && \
  apt install -y curl && \
  apt clean all
RUN gem install aws-sdk-s3 --version '~> 1'

COPY entrypoint.sh /opt/
COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
