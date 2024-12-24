-- 유저
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 ID, 자동 증가',
                       user_id VARCHAR(50) NOT NULL COMMENT '사용자 식별자 (이메일)',
                       name VARCHAR(50) NOT NULL COMMENT '사용자 이름',
                       password VARCHAR(255) NOT NULL COMMENT '사용자 비밀번호, 암호화하여 저장',
                       id_type VARCHAR(255) NOT NULL CHECK (id_type IN ('REG_NO', 'BUSINESS_NO')) COMMENT '사용자 타입 (개인: REG_NO, 법인: BUSINESS_NO)',
                       id_value VARCHAR(255) NOT NULL COMMENT '식별자 값 (주민등록번호 또는 사업자 등록번호, 암호화하여 저장)',
                       UNIQUE(user_id)
);

CREATE TABLE refresh_tokens (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 기본 키, AUTO_INCREMENT
                                user_mapping_id BIGINT, -- UserEntity와 매핑될 외래 키 (insertable=false, updatable=false에 주의)
                                token VARCHAR(255), -- 토큰 값
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 갱신 시간

    -- 외래 키 제약조건 (USER_MAPPING_ID에 대해 USER 테이블과 매핑)
                                CONSTRAINT FK_USER_MAPPING_ID FOREIGN KEY (user_mapping_id) REFERENCES users(id)
);