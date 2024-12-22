# HTS STEP SERVER

```text
주요 특징:
- Spring Boot와 Spring MVC(Servlet) 기반
- 데이터베이스는 H2를 사용하며 MyBatis(iBatis) 및 JPA를 동시에 지원
- CQRS 패턴에 기반한 확장성: NoSQL(elasticsearch) 추가 가능
- Swagger UI 3로 API 문서화
- 모든 API 호출에 인증 토큰 검증
```

## step-hts-server Run
>mvn clean spring-boot:run