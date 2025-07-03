# Java 17 환경 이미지 사용
FROM openjdk:17-jdk-slim

# jar 파일 복사
COPY build/libs/heartsocpyBeckEnd-0.0.1-SNAPSHOT.jar app.jar

# jar 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]