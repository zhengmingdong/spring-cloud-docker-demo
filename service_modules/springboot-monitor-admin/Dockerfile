FROM harbor.zhuiyinanian.cn/ops/jdk:8
COPY target/springboot-monitor-admin-1.0.0-SNAPSHOT.jar admin-server.jar
ENTRYPOINT exec java -Duser.timezone=Asia/Shanghai -Dfile.encoding=utf-8 -jar admin-server.jar --spring.profiles.active=pre

