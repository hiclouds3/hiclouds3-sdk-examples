FROM python:3.8

COPY requirements.txt /opt/
RUN pip install -r /opt/requirements.txt

COPY sample /opt/sample

WORKDIR /opt/sample
ENTRYPOINT ["python", "PythonSerialTest.py"]
ENTRYPOINT ["/bin/sh", "-c" , "python PythonSerialTest.py && while true; do sleep 15; done"]
