-- 항공 정보 테이블
CREATE TABLE tour_airfly (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        capacity INT NOT NULL,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP
);

-- 호텔 정보 테이블
CREATE TABLE tour_hotel (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       location VARCHAR(255),
                       stars INT NOT NULL,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);

-- 패키지 정보 테이블
CREATE TABLE tour_pkg (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(255) NOT NULL,
                     details TEXT,
                     price DECIMAL(10, 2),
                     created_at TIMESTAMP,
                     updated_at TIMESTAMP
);