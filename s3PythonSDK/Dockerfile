FROM python:3.8-alpine

RUN apk --no-cache add curl

COPY requirements.txt entrypoint.sh /opt/
RUN pip install -r /opt/requirements.txt

COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
