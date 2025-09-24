# Gateway TLS 인증서 적용방법

##  STEP 01 경로이동
경로 : 프로젝트 ROOT 이동
> /Users/milk/workspace/boilerplate-maven/channel-gateway

## STEP 02 p12,crt 파일생성 스크립트
```shell
#!/bin/bash

# 개발용 자체 서명 인증서 생성 스크립트

KEYSTORE_PATH="/Users/milk/workspace/boilerplate-maven/channel-gateway/src/main/resources/keystore.p12"

echo "SSL 인증서 생성 시작..."

# 1. PKCS12 형식의 keystore 생성 (Spring Boot 권장)
keytool -genkeypair \
  -alias localhost \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore "$KEYSTORE_PATH" \
  -storepass changeit \
  -validity 365 \
  -dname "CN=localhost, OU=Development, O=farfarcoder, L=Seoul, ST=Seoul, C=KR" \
  -ext SAN=dns:localhost,ip:127.0.0.1

echo "인증서 생성 완료. 정보 확인 중..."

# 2. 인증서 정보 확인 (절대 경로 사용)
keytool -list -v -keystore "$KEYSTORE_PATH" -storepass changeit

echo "인증서를 .crt 파일로 추출 중..."

# 3. 인증서를 .crt 파일로 추출 (브라우저 신뢰 추가용)
keytool -export \
  -alias localhost \
  -keystore "$KEYSTORE_PATH" \
  -storepass changeit \
  -file localhost.crt

echo ""
echo "===== SSL 인증서 생성 완료 ====="
echo "- keystore.p12: Spring Boot 애플리케이션용"
echo "- localhost.crt: 브라우저에 신뢰할 수 있는 인증서로 추가"
echo ""
echo "생성된 파일 위치:"
echo "- Keystore: $KEYSTORE_PATH"
echo "- Certificate: $(pwd)/localhost.crt"
echo ""
echo "다음 단계:"
echo "1. application-local.yml에 SSL 설정 추가"
echo "2. 애플리케이션 재시작"
echo "3. https://localhost:8888/hello 접속 테스트"
```
```text
IllegalFormatConversionException 오류는 Java keytool의 알려진 버그로 실제 인증서 생성에는 영향을 주지 않습니다.
```

## SETP 03
### 파일이 정상적으로 생성되었는지 확인
> ls -la src/main/resources/keystore.p12
> ls -la localhost.crt

### 간단한 인증서 정보 확인 (오류 없이)
> keytool -list -keystore src/main/resources/keystore.p12 -storepass changeit
### 정상일 경우 
```text 
키 저장소 유형: PKCS12
키 저장소 제공자: SUN

키 저장소에 1개의 항목이 포함되어 있습니다.

localhost, 2025. 9. 19., PrivateKeyEntry,
인증서 지문(SHA-256): 61:85:65:62:E6:42:19:6E:3F:0F:EB:30:03:8E:F7:19:76:D1:51:4E:A5:72:40:85:E4:2B:65:08:2E:42:92:74
```