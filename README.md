# boilerplate-maven
## maven

## 프로젝트 구조
 
```text
gateway/                   <-- 루트 프로젝트 (Parent)  
├── pom.xml                <-- 부모 POM (공통 설정)  
├── auth-server/           <-- 인증 서버 
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드  
├── domain/                <-- GATEWAY 도메인
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── partner-gateway/       <-- 제휴사 gateway  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── step-hts-server/       <-- hts 테스트 서버  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── tour-api-server/       <-- tour api  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── util/                  <-- GATEWAY 공통 유틸  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
```

## 멀티모듈 만들기 
생성가능한 artifactId명령어 모음
```text
Choose archetype:

1: internal -> org.apache.maven.archetypes:maven-archetype-archetype (An archetype which contains a sample archetype.)
2: internal -> org.apache.maven.archetypes:maven-archetype-j2ee-simple (An archetype which contains a simplifed sample J2EE application.)
3: internal -> org.apache.maven.archetypes:maven-archetype-plugin (An archetype which contains a sample Maven plugin.)
4: internal -> org.apache.maven.archetypes:maven-archetype-plugin-site (An archetype which contains a sample Maven plugin site.
      This archetype can be layered upon an existing Maven plugin project.)
5: internal -> org.apache.maven.archetypes:maven-archetype-portlet (An archetype which contains a sample JSR-268 Portlet.)
6: internal -> org.apache.maven.archetypes:maven-archetype-profiles ()
7: internal -> org.apache.maven.archetypes:maven-archetype-quickstart (An archetype which contains a sample Maven project.)
8: internal -> org.apache.maven.archetypes:maven-archetype-site (An archetype which contains a sample Maven site which demonstrates
      some of the supported document types like APT, XDoc, and FML and demonstrates how
      to i18n your site. This archetype can be layered upon an existing Maven project.)
9: internal -> org.apache.maven.archetypes:maven-archetype-site-simple (An archetype which contains a sample Maven site.)
10: internal -> org.apache.maven.archetypes:maven-archetype-webapp (An archetype which contains a sample Maven Webapp project.)

```
>mvn archetype:generate -DarchetypeCatalog=internal

```
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=tour-api-server \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false    
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=step-hts-server \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=partner-gateway \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=auth-server \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false            
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=domain \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false

mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=util \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false           
```

# maven 명령어
## maven 기본 클린 및 install
>mvn clean install
## maven 실행 및 에러로그 추가 컴파일
>mvn clean compile -e
## main maven 실행
>mvn spring-boot:run


#시나리오 테스트 
```text
partner-gateway 제휴사 게이트웨이
step-hts-server HTS dummy SERVER 구성

```
## hosts 설정
```text
127.0.0.1 partner-gateway.lottecard.com
127.0.0.1 hts.com
```


## step-hts-server 실행
>mvn clean spring-boot:run
![스크린샷 2024-12-22 오후 9 07 59](https://github.com/user-attachments/assets/25f3a3de-8d7f-4319-86be-6ee200909e3a)

## Swagger UI
<img width="1054" alt="스크린샷 2024-12-22 오후 9 11 03" src="https://github.com/user-attachments/assets/f839934a-68a4-4da7-8ee7-9bbb1e9fbe9b" />
<img width="1028" alt="스크린샷 2024-12-22 오후 9 12 52" src="https://github.com/user-attachments/assets/13aa67d0-7b67-46de-8221-764e37e052c2" />


## partner-gateway 실행
>mvn clean spring-boot:run
![스크린샷 2024-12-22 오후 9 10 20](https://github.com/user-attachments/assets/493b63ec-45ac-4862-8eb6-7807ac63d635)

## 게이트웨이 접근 URI없는 접근시
http://partner-gateway.lottecard.com:8080/
<img width="1162" alt="스크린샷 2024-12-22 오후 9 15 18" src="https://github.com/user-attachments/assets/8a745eae-9c5b-4706-ac3b-959c2237d608" />

## 제휴사 게이트웨이설정
hts 도메인으로 오는 정보에 대해서 아래와같이 
/api/hts 를 붙이도록 
기존 제휴사 API URI에는 /hts로만 진행됨.
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: step-hts-server
          predicates:
            - Path=/api/hts/**
          uri: http://localhost:18080
          filters:
            - RewritePath=/api/hts/(?<segment>.*), /hts/$\{segment}

```
## 제휴사 게이트웨이에 정의된 URI패턴으로 접근시 
http://partner-gateway.lottecard.com:8080/api/hts/airflies

## 정상적으로 HTS 도메인에 항공 리스트 호출
<img width="1161" alt="스크린샷 2024-12-22 오후 9 18 32" src="https://github.com/user-attachments/assets/53db8244-7164-4fdc-be26-67472fe26982" />
