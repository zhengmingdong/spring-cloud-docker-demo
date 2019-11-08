FROM harbor.zhuiyinanian.cn/ops/jdk:8
COPY target/gateway-server-1.0.0-SNAPSHOT.jar gateway-server.jar
ENTRYPOINT exec java  -Duser.timezone=Asia/Shanghai -Dfile.encoding=utf-8 -jar gateway-server.jar --spring.profiles.active=pre