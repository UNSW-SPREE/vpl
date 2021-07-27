
FROM solarkennedy/wine-x11-novnc-docker

WORKDIR /

RUN apt-get update \
    && apt-get install -y unzip

RUN wget https://builds.openlogic.com/downloadJDK/openlogic-openjdk-jre/8u262-b10/openlogic-openjdk-jre-8u262-b10-windows-x32.zip
RUN unzip openlogic-openjdk-jre-8u262-b10-windows-x32.zip

WORKDIR /root/prefix32/drive_c/ProgramData
Run mkdir VirtualProductionLine
WORKDIR /root/prefix32/drive_c/ProgramData/VirtualProductionLine

COPY pc1d/Pc1d.exe pc1d/Pc1d.opt pc1d/

COPY dist/ config.dat acidtexturing.dat README.txt .
COPY help/ help/
COPY icons/ icons/
COPY images/ images/

ENV WINEDLLOVERRIDES="mscoree,mshtml="

ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# RUN wine reg.exe ADD "HKEY_CURRENT_USER\Software\Wine\DllOverrides" "/v" "uxtheme-gtk" "/t" "REG_SZ" "/d" ""

EXPOSE 8080


CMD ["/usr/bin/supervisord"]