FROM python:3.9-slim
RUN mkdir /app
WORKDIR /app

ADD requirements.txt /app
RUN pip3 install -r requirements.txt

ADD . /app

CMD ["gunicorn", "-w 4", "-b", "0.0.0.0:8000", "main:app"]