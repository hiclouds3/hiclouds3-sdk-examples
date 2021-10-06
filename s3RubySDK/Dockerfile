FROM ruby:3-alpine

RUN apk --no-cache add curl
RUN gem install aws-sdk-s3 --version '~> 1'

COPY entrypoint.sh /opt/
COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
