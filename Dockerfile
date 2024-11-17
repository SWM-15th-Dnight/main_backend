FROM gradle:7.5.0-jdk17 AS build
WORKDIR /app

# 필요한 파일들을 복사
COPY --chown=gradle:gradle . /app

# 프로젝트 빌드 (JAR 파일 생성)
RUN ./gradlew build

# 두 번째 스테이지: 최종 실행 환경
FROM openjdk:17
WORKDIR /root/

# 시간대 설정
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone

RUN mkdir -p /root/ics_tmp && chmod 755 /root/ics_tmp

# 빌드 스테이지에서 생성된 JAR 파일을 복사
COPY --from=build /app/build/libs/*.jar /root/app.jar

# 애플리케이션 실행
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/root/app.jar"]
