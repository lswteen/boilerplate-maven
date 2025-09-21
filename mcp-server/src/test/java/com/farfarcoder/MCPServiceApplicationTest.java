package com.farfarcoder;


import com.farfarcoder.mcp.service.CicdToolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = MCPServiceApplication.class)  // 명시적으로 지정
@ActiveProfiles("test")
public class MCPServiceApplicationTest {


    @Test
    void contextLoads() {
        // Spring Context 로드 테스트
    }
}
