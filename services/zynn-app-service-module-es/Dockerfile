FROM harbor.zhuiyinanian.cn/ops/jdk:8
COPY target/zynn-app-service-module-es-1.0.0-SNAPSHOT.jar es-service.jar
ENTRYPOINT exec java -Duser.timezone=Asia/Shanghai -Dfile.encoding=utf-8 -jar es-service.jar --spring.profiles.active=pre
