FROM rust:1-slim

RUN apt update && \
  apt install -y curl && \
  apt clean all

COPY entrypoint.sh /opt/
COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]
RUN cd /opt/sample && \
  cargo install --path . && \
  cargo clean

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
