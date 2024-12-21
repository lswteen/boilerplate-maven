# boilerplate-maven
maven

## 프로젝트 구조
 
```text
gateway/                   <-- 루트 프로젝트 (Parent)  
├── pom.xml                <-- 부모 POM (공통 설정)  
├── auth/                  <-- 인증  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드  
├── domain/                <-- 도메인
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── gateway/               <-- gateway  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── step-backend-server/   <-- 백엔드 서버 모듈  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── step-client-server/    <-- client  
│   ├── pom.xml            <-- 모듈 POM  
│   └── src/main/java      <-- Java 소스 코드 
├── util/                  <-- 공통유틸  
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
mvn archetype:generate -DarchetypeCatalog=internal

```
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=step-backend-server \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false    
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=step-client-server \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=gateway \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
    
mvn archetype:generate \
    -DgroupId=com.farfarcoder \
    -DartifactId=auth \
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
mvn clean install
## maven 실행 및 에러로그 추가 컴파일
mvn clean compile -e
## main maven 실행
mvn spring-boot:run