#!/bin/bash

# HTTPS 테스트 스크립트
# 사용법: ./test-https.sh

# 색상 정의
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

HTTPS_URL="https://localhost:8443"
HTTP_URL="http://localhost:8080"

echo "================================================"
echo "   Channel Gateway HTTPS 테스트"
echo "================================================"
echo ""

# 1. HTTPS 연결 테스트 (Self-signed 인증서 허용)
echo -e "${YELLOW}[1] HTTPS 연결 테스트${NC}"
echo "URL: ${HTTPS_URL}/hello"
RESPONSE=$(curl -k -s -o /dev/null -w "%{http_code}" ${HTTPS_URL}/hello)

if [ "$RESPONSE" == "200" ]; then
    echo -e "${GREEN}✓ HTTPS 연결 성공 (HTTP $RESPONSE)${NC}"
    curl -k -s ${HTTPS_URL}/hello
    echo ""
else
    echo -e "${RED}✗ HTTPS 연결 실패 (HTTP $RESPONSE)${NC}"
fi
echo ""

# 2. SSL 인증서 정보 확인
echo -e "${YELLOW}[2] SSL 인증서 정보${NC}"
echo | openssl s_client -connect localhost:8443 -servername localhost 2>/dev/null | \
  openssl x509 -noout -subject -issuer -dates 2>/dev/null || \
  echo -e "${RED}✗ SSL 인증서 정보 조회 실패${NC}"
echo ""

# 3. TLS 버전 확인
echo -e "${YELLOW}[3] 지원하는 TLS 프로토콜 확인${NC}"
for version in tls1_2 tls1_3; do
    RESULT=$(openssl s_client -connect localhost:8443 -$version </dev/null 2>/dev/null | grep "Protocol" | head -1)
    if [ -n "$RESULT" ]; then
        echo -e "${GREEN}✓ ${version^^} 지원: $RESULT${NC}"
    else
        echo -e "${RED}✗ ${version^^} 미지원${NC}"
    fi
done
echo ""

# 4. HTTP/2 지원 확인
echo -e "${YELLOW}[4] HTTP/2 지원 확인${NC}"
HTTP2_CHECK=$(curl -k -s -I --http2 ${HTTPS_URL}/hello 2>/dev/null | grep "HTTP/2")
if [ -n "$HTTP2_CHECK" ]; then
    echo -e "${GREEN}✓ HTTP/2 지원${NC}"
    echo "$HTTP2_CHECK"
else
    echo -e "${YELLOW}⚠ HTTP/2 미지원 (HTTP/1.1 사용)${NC}"
fi
echo ""

# 5. 보안 헤더 확인
echo -e "${YELLOW}[5] 보안 헤더 확인${NC}"
HEADERS=$(curl -k -s -I ${HTTPS_URL}/hello)

check_header() {
    HEADER_NAME=$1
    if echo "$HEADERS" | grep -i "$HEADER_NAME" > /dev/null; then
        VALUE=$(echo "$HEADERS" | grep -i "$HEADER_NAME")
        echo -e "${GREEN}✓ $VALUE${NC}"
    else
        echo -e "${RED}✗ $HEADER_NAME 헤더 없음${NC}"
    fi
}

check_header "Strict-Transport-Security"
check_header "X-Frame-Options"
check_header "X-Content-Type-Options"
check_header "Referrer-Policy"
echo ""

# 6. Actuator 엔드포인트 테스트
echo -e "${YELLOW}[6] Actuator Health Check${NC}"
HEALTH_RESPONSE=$(curl -k -s ${HTTPS_URL}/actuator/health)
if echo "$HEALTH_RESPONSE" | grep "UP" > /dev/null; then
    echo -e "${GREEN}✓ Health Check 성공${NC}"
    echo "$HEALTH_RESPONSE" | jq '.' 2>/dev/null || echo "$HEALTH_RESPONSE"
else
    echo -e "${RED}✗ Health Check 실패${NC}"
fi
echo ""

# 7. HTTP → HTTPS 리다이렉션 테스트 (활성화된 경우)
echo -e "${YELLOW}[7] HTTP → HTTPS 리다이렉션 테스트${NC}"
REDIRECT_TEST=$(curl -s -o /dev/null -w "%{http_code}|%{redirect_url}" ${HTTP_URL}/hello 2>/dev/null)
HTTP_CODE=$(echo $REDIRECT_TEST | cut -d'|' -f1)
REDIRECT_URL=$(echo $REDIRECT_TEST | cut -d'|' -f2)

if [ "$HTTP_CODE" == "301" ] || [ "$HTTP_CODE" == "308" ]; then
    echo -e "${GREEN}✓ HTTP → HTTPS 리다이렉션 활성화 (HTTP $HTTP_CODE)${NC}"
    echo "  Redirect to: $REDIRECT_URL"
elif [ "$HTTP_CODE" == "000" ]; then
    echo -e "${YELLOW}⚠ HTTP 포트(8080) 미사용 (리다이렉션 비활성화)${NC}"
else
    echo -e "${YELLOW}⚠ 리다이렉션 미설정 (HTTP $HTTP_CODE)${NC}"
fi
echo ""

echo "================================================"
echo -e "${GREEN}   테스트 완료${NC}"
echo "================================================"