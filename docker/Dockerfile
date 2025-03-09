# docker.io/library/ubuntu:24.02: not found
FROM ubuntu:24.04

RUN apt update && apt install -y \
    openjdk-8-jdk \
    curl \
    unzip \
    zip \
    git \
    wget

RUN curl -s https://get.sdkman.io | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install kotlin"

ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"
ENV PATH="$PATH:/root/.sdkman/candidates/kotlin/current/bin"

WORKDIR /app

ARG GRADLE_VERSION=8.7

RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    mkdir /opt/gradle && \
    unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV PATH="/opt/gradle/gradle-${GRADLE_VERSION}/bin:$PATH"

COPY app /app
COPY app/src/main/java/ /app/src/main/java/

RUN gradle build

CMD ["gradle", "run"]