
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

#     && apt-get install -y unzip \
#     && wget https://builds.openlogic.com/downloadJDK/openlogic-openjdk-jre/8u262-b10/openlogic-openjdk-jre-8u262-b10-windows-x32.zip \
#     && unzip openlogic-openjdk-jre-8u262-b10-windows-x32.zip \
#     && wget https://sourceforge.net/projects/fmj/files/fmj/fmj-20070928-0938/fmj-20070928-0938.zip/download \
#     && unzip download -d fmj
    

# # WORKDIR /root/prefix32/drive_c/ProgramData
# # Run mkdir VirtualProductionLine
# WORKDIR /root/prefix32/drive_c/ProgramData/VirtualProductionLine

# RUN mkdir lib \
#     && cp /fmj/fmj.jar lib/jmf.jar

# # COPY pc1d/Pc1d.exe pc1d/Pc1d.opt pc1d/

# # COPY dist/VPL.jar config.dat acidtexturing.dat README.txt .
# # COPY help/ help/
# # COPY icons/ icons/
# # COPY images/ images/


ENV WINEDLLOVERRIDES="mscoree,mshtml=" PC1D_FOLDER="/vpl/pc1d" PC1D_EXE="/usr/bin/wine /vpl/pc1d/Pc1d.exe"

ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# # RUN wine reg.exe ADD "HKEY_CURRENT_USER\Software\Wine\DllOverrides" "/v" "uxtheme-gtk" "/t" "REG_SZ" "/d" ""

EXPOSE 8080


CMD ["/usr/bin/supervisord"]