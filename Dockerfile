# 使用Maven镜像作为构建阶段
FROM maven:3.8.8-openjdk-8 AS builder

# 设置工作目录为/app
WORKDIR /app

# 复制pom.xml并下载依赖
COPY pom.xml .
RUN mvn -B -e -C -T 1C dependency:go-offline

# 复制整个项目并构建应用程序
COPY . .
RUN mvn -B -e -o -T 1C package

# 使用轻量级的JRE镜像作为最终镜像
FROM openjdk:8-jre-slim

# 设置工作目录为/app
WORKDIR /app

# 从构建阶段复制构建的jar文件
COPY --from=builder /app/target/*.jar ./app.jar

# 暴露应用程序的端口（如果需要）
EXPOSE 8080

# 运行应用程序
CMD ["java", "-jar", "online_yiche-1.0-SNAPSHOT.jar"]
