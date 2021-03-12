package com.ram.testcontainersdemo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testable
public class PostgreSqlContainerLiveTest {
  
	private  PostgreSQLContainer postgreSQLContainer = null;
    @BeforeEach
    public void init() {
	 
    	postgreSQLContainer=	new PostgreSQLContainer().withUsername("user01").withPassword("pass01") .withDatabaseName("testDatabase");
    	postgreSQLContainer.start();
    }
    
    @Test
    public void whenSelectQueryExecuted_thenResulstsReturned() throws Exception {
        ResultSet resultSet = performQuery(postgreSQLContainer, "SELECT 1");
        resultSet.next();
        int result = resultSet.getInt(1);
        assertEquals(1, result);
    }

    private ResultSet performQuery(PostgreSQLContainer postgres, String query) throws SQLException {
        String jdbcUrl = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();
        Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
        return conn.createStatement()
            .executeQuery(query);
    }
}
