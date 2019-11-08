FROM harbor.zhuiyinanian.cn/ops/jdk:8
COPY target/zynn-app-service-module-user-1.0.0-SNAPSHOT.jar user-service.jar
ENTRYPOINT exec java -Duser.timezone=Asia/Shanghai -Dfile.encoding=utf-8 -jar user-service.jar --spring.profiles.active=pre
