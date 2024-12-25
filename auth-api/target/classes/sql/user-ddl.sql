-- 유저
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id VARCHAR(50) NOT NULL UNIQUE, -- 사용자 식별자 (이메일)
                       name VARCHAR(50) NOT NULL,          -- 사용자 이름
                       password VARCHAR(255) NOT NULL,     -- 사용자 비밀번호
                       id_type VARCHAR(255) NOT NULL CHECK (id_type IN ('REG_NO', 'BUSINESS_NO')), -- 사용자 타입
                       id_value VARCHAR(255) NOT NULL      -- 식별자 값
);


CREATE TABLE refresh_tokens (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                user_mapping_id BIGINT NOT NULL,    -- users 테이블의 사용자 ID 참조
                                token VARCHAR(255) NOT NULL,        -- 리프레시 토큰 값
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
                                updated_at TIMESTAMP AS CURRENT_TIMESTAMP NOT NULL, -- 갱신 시간, 매번 트리거 설정
                                CONSTRAINT FK_USER_MAPPING_ID FOREIGN KEY (user_mapping_id) REFERENCES users(id)
                                    ON DELETE CASCADE
);