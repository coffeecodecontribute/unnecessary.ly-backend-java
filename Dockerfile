# syntax=docker/dockerfile:experimental
# Build container
FROM --platform=$TARGETPLATFORM maven:3-openjdk-15 AS build
ARG DIBS_TARGET
ARG TARGETPLATFORM

WORKDIR /app

RUN curl -Lo /tmp/dibs https://nx904.your-storageshare.de/s/ZWxkmmQW37fHt9J/download
RUN install /tmp/dibs /usr/local/bin

ADD . .

RUN dibs -generateSources
RUN dibs -build

# Run container
FROM --platform=$TARGETPLATFORM openjdk:15-ea
ARG DIBS_TARGET
ARG TARGETPLATFORM

COPY --from=build /app/target/unnecessary.ly-backend* /usr/local/bin/unnecessary-ly-backend.jar

CMD java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/bin/unnecessary-ly-backend.jar
