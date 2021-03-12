package com.ram.testcontainersdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.MySQLContainerProvider;
import org.testcontainers.containers.PostgreSQLContainer;


public class MySQLContainerLiveTest {

	 private MySQLContainerProvider mysql = null;
	 private JdbcDatabaseContainer mysqlContainer = null;
	 @BeforeEach
	 public void init() {
		 mysqlContainer = new MySQLContainerProvider().newInstance();
	                
	                
		 mysqlContainer.start();
		 System.out.println(mysqlContainer.getDatabaseName());
	 }
	 @Test
	 public void testConnection() throws SQLException {
		 ResultSet resultSet = performQuery(mysqlContainer, "SELECT 1");
		 resultSet.next();
	        int result = resultSet.getInt(1);
	        assertEquals(1, result);
	        
	 }
	 
	 private ResultSet performQuery(JdbcDatabaseContainer mysqlContainer, String query) throws SQLException {
		  
		 String jdbcUrl = mysqlContainer.getJdbcUrl();
	        String username = mysqlContainer.getUsername();
	        String password = mysqlContainer.getPassword();
	        Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
	        return conn.createStatement()
	            .executeQuery(query);
	    }	 
}
