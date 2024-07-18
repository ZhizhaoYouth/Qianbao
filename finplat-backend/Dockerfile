# Docker 镜像构建
FROM maven:3.8.1-jdk-8-slim as builder

RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Run the web service on container startup.
CMD ["java","-jar","/app/target/financial-manage-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]