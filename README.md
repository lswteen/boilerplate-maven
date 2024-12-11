# boilerplate-maven
maven

## 프로젝트 구조
 
```text
gateway/                   <-- 루트 프로젝트 (Parent)  
├── pom.xml                <-- 부모 POM (공통 설정)  
├── step-client-server/    <-- 클라이언트 서버 모듈  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드  
├── step-backend-server/   <-- 백엔드 서버 모듈  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
```

## 멀티모듈 만들기 
```
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=step-backend-server \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
```


# maven 명령어

## maven 기본 클린 및 install
mvn clean install
## maven 실행 및 에러로그 추가 컴파일
mvn clean compile -e
## main maven 실행
mvn spring-boot:run