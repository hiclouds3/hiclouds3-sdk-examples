FROM python:3.8-slim

RUN apt update && \
  apt install -y curl && \
  apt clean all

COPY requirements.txt entrypoint.sh /opt/
RUN pip install -r /opt/requirements.txt

COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
