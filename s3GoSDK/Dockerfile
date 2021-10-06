FROM golang:1.17

COPY entrypoint.sh /opt/
COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]
RUN cd /opt/sample/ && go mod init mys3test && go get

WORKDIR /opt/sample
ENTRYPOINT ["/opt/entrypoint.sh"]
