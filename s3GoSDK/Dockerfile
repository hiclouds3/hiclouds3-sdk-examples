FROM golang:1.17

COPY entrypoint.sh /opt/
COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
