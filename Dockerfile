#Adapted from https://github.com/solarkennedy/wine-x11-novnc-docker
FROM ubuntu:focal

ENV HOME /root
ENV DEBIAN_FRONTEND noninteractive
ENV LC_ALL C.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8

RUN dpkg --add-architecture i386 && \
    apt-get update && apt-get -y install python2 python-is-python2 xvfb x11vnc xdotool wget tar supervisor net-tools fluxbox gnupg2 unzip && \
    wget -O - https://dl.winehq.org/wine-builds/winehq.key | apt-key add -  && \
    echo 'deb https://dl.winehq.org/wine-builds/ubuntu/ focal main' |tee /etc/apt/sources.list.d/winehq.list && \
    apt-get update && apt-get -y install winehq-stable  && \
    # mkdir /opt/wine-stable/share/wine/mono && wget -O - https://dl.winehq.org/wine/wine-mono/4.9.4/wine-mono-bin-4.9.4.tar.gz |tar -xzv -C /opt/wine-stable/share/wine/mono && \
    # mkdir /opt/wine-stable/share/wine/gecko && wget -O /opt/wine-stable/share/wine/gecko/wine-gecko-2.47.1-x86.msi https://dl.winehq.org/wine/wine-gecko/2.47.1/wine-gecko-2.47.1-x86.msi && wget -O /opt/wine-stable/share/wine/gecko/wine-gecko-2.47.1-x86_64.msi https://dl.winehq.org/wine/wine-gecko/2.47.1/wine-gecko-2.47.1-x86_64.msi && \
    apt-get -y full-upgrade && apt-get clean && rm -rf /var/lib/apt/lists/*
ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf

ENV WINEPREFIX /root/prefix32
ENV WINEARCH win32
ENV DISPLAY :0

WORKDIR /root/
RUN wget -O - https://github.com/novnc/noVNC/archive/v1.1.0.tar.gz | tar -xzv -C /root/ && mv /root/noVNC-1.1.0 /root/novnc && ln -s /root/novnc/vnc_lite.html /root/novnc/index.html && \
    wget -O - https://github.com/novnc/websockify/archive/v0.9.0.tar.gz | tar -xzv -C /root/ && mv /root/websockify-0.9.0 /root/novnc/utils/websockify


# RUN wget -O - https://dl.winehq.org/wine-builds/winehq.key | apt-key add -  && \
#     echo 'deb https://dl.winehq.org/wine-builds/ubuntu/ focal main' |tee /etc/apt/sources.list.d/winehq.list && \
#     apt-get update && apt-get -y install ufw winetricks


WORKDIR /

RUN wget https://builds.openlogic.com/downloadJDK/openlogic-openjdk-jre/8u262-b10/openlogic-openjdk-jre-8u262-b10-windows-x32.zip
RUN unzip openlogic-openjdk-jre-8u262-b10-windows-x32.zip

WORKDIR /root/prefix32/drive_c/ProgramData
Run mkdir VirtualProductionLine
WORKDIR /root/prefix32/drive_c/ProgramData/VirtualProductionLine

COPY pc1d/Pc1d.exe pc1d/pc1d.exe
COPY pc1d/Pc1d.opt pc1d/pc1d.opt

COPY dist/ .
COPY help/ help/
COPY icons/ icons/
COPY images/ images/
copy config.dat .
copy acidtexturing.dat .
copy README.txt .

ENV WINEDLLOVERRIDES="mscoree,mshtml="

ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# RUN wine reg.exe ADD "HKEY_CURRENT_USER\Software\Wine\DllOverrides" "/v" "uxtheme-gtk" "/t" "REG_SZ" "/d" ""

EXPOSE 8080


CMD ["/usr/bin/supervisord"]