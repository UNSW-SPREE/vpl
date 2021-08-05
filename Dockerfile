
FROM maven:3.8-openjdk-16-slim AS Build

RUN mkdir /root/.m2

WORKDIR /vpl 


COPY . .

WORKDIR /vpl/vpl-app

RUN --mount=type=cache,target=/root/.m2 mvn clean package


FROM solarkennedy/wine-x11-novnc-docker

WORKDIR /

RUN apt-get update && \
    apt-get install -y openjdk-16-jre && \
    apt-get clean;

WORKDIR vpl

COPY vpl-app/pc1d /vpl/pc1d
COPY --from=Build /vpl/vpl-app/target/vpl-app-1.0-SNAPSHOT-shaded.jar ./vpl.jar


ENV WINEDLLOVERRIDES="mscoree,mshtml=" PC1D_FOLDER="/vpl/pc1d" PC1D_EXE="/usr/bin/wine /vpl/pc1d/Pc1d.exe"

ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf


EXPOSE 8080


CMD ["/usr/bin/supervisord"]