package com.example.dllab;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IntegrationMockHelper extends AbstractTestExecutionListener {
    public static final String NON_ASCII = "NonAsciiCharacters";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        // 현재 실행된 포트 지정
        RestAssured.port = this.port;

        // 참조 무결성 비활성화
        jdbcTemplate.execute("SET session_replication_role = 'replica';");

        // public 스키마에 있는 모든 테이블 조회 후 'TRUNCATE TABLE (TABLE_NAME) RESTART IDENTITY CASCADE;' 형식의 쿼리로 생성
        List<String> truncateAllTablesQuery = jdbcTemplate.queryForList(
                "SELECT 'TRUNCATE TABLE \"' || tablename || '\" RESTART IDENTITY CASCADE;' " +
                        "FROM pg_tables " +
                        "WHERE schemaname = 'public';",
                String.class);

        // 데이터베이스의 모든 테이블 초기화
        truncateAllTables(truncateAllTablesQuery);

        // 참조 무결성 재활성화
        jdbcTemplate.execute("SET session_replication_role = 'origin';");
    }

    private void truncateAllTables(List<String> truncateAllTablesQuery) {
        // truncate 쿼리 실행
        for (String truncateQuery : truncateAllTablesQuery) {
            jdbcTemplate.execute(truncateQuery);
        }
    }
}

