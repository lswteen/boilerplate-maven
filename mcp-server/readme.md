# MCP와 Spring AI Tool의 연관성

개념 정리
Tool Calling: AI 모델이 외부 API나 도구와 상호작용할 수 있게 하는 패턴 Tool Calling

Information Retrieval: 외부 소스에서 정보 검색 (DB, 웹서비스, 파일시스템)
Taking Action: 소프트웨어 시스템에서 액션 수행 (이메일 전송, DB 레코드 생성)

## 우리 프로젝트 적용
CICD 정보 검색: Bitbucket 프로젝트 조회, Bamboo 빌드 상태 확인 Tool Calling
CICD 액션: 빌드 트리거, 배포 실행 등

## 핵심 내용
@Tool 어노테이션: 메서드를 AI가 호출할 수 있는 도구로 변환
@ToolParam: 매개변수에 대한 설명과 필수/선택 여부 지정
자동 JSON 스키마 생성: AI 모델이 도구 호출 방법 이해
ToolCallback 인터페이스: 도구 정의와 실행 로직 제공

```java
@Tool(description = "Search CICD projects by key")
public String searchProject(@ToolParam(description = "Project key like LCTC") String projectKey) {
    // SCM API 호출로 프로젝트 검색
    return "Found project: " + projectKey;
}
```