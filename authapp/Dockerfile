# syntax=docker/dockerfile:1

FROM python:3.10-slim-buster

WORKDIR /fetch_app

COPY . /fetch_app

RUN pip3 install --no-cache-dir -r requirements.txt

ENV FLASK_APP=app.py
EXPOSE 5000

CMD ["flask", "run", "--host=0.0.0.0"]