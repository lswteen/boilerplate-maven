# Boilerplate-Maven With Java17 Spring 3.4.0

## 프로젝트 구조
### 좋은 어플리케이션은 만들떈 알수 없다.
### 만들고 나서 수정을 할때 최소한의 작업과 장애 발생 영향도를 최소화 할수 있어야 한다.!
### 무조건 나누고 쪼갠다고 좋은 아키텍처라 할수없다.
### 상황에 맞게 아키텍처를 설계하고 구성해야 한다.
### MSA는 정답이 아니다!!


```html
maven POM으로 구성된 미니 MSA 보일러플레이트 레이어드 MVC 아키텍처 
DDD 도메인 드리븐 디자인 
부모 자식간 계층형 멀티모듈 각 도메인 주도적인 레이어
단위 통합 테스트 구성
소규모 리소스를 기반으로 확장 가능한 구조
```
## 계층 구조 설명
```text
gateway/                   <-- 루트 프로젝트 (Parent)  
├── pom.xml                <-- 부모 POM (공통 설정)  
├── gateway-domain/        <-- GATEWAY 도메인
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── gateway-util/          <-- GATEWAY 공통 유틸  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── partner-gateway/       <-- 제휴사 gateway  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드
├── channel-gateway/       <-- 채널 gateway  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 

├── step-hts-server/       <-- 항공,여행,패키지 hts 테스트 서버  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 

├── web-common/            <-- 웹 공통 도메인 
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드  
├── auth-api/              <-- 유저 인증/인가 서버 
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드  
├── tour-api/              <-- tour api  
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

## Web Boilerplate
투어, 유저&인증인가, 공통 에러 처리
```
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=tour-api \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false       
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=auth-api \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false            

mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=web-common \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
```

## HTS Dummy Server
```text
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=step-hts-server \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
```

## Gateway Boilerplate   
제휴사, 파트너, 공통 domain, 공통 유틸
``` 
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=partner-gateway \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false      
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=gateway-domain \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false

mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=gateway-util \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false           
```

# maven 명령어
## maven 기본 클린 및 install
>mvn clean install
## maven 실행 및 에러로그 추가 컴파일
>mvn clean compile -e
## main maven 실행
>mvn clean spring-boot:run


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


## intellij 코드충돌시
![스크린샷 2024-12-24 오후 11 16 01](https://github.com/user-attachments/assets/9b3de804-32dd-448c-89f0-5deced93a127)
