FROM python:3.8

COPY requirements.txt /opt/
RUN pip install -r /opt/requirements.txt

COPY sample /opt/sample

COPY entrypoint.sh /
WORKDIR /opt
ENTRYPOINT ["/entrypoint.sh"]
