package com.payroll;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeArchivedIntegrationTest {
    @LocalServerPort int port;
    @Autowired JdbcTemplate jdbcTemplate;
    @BeforeEach void setUp() {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = port;
        jdbcTemplate.update("DELETE FROM employees");
        jdbcTemplate.update("INSERT INTO employees (name, department, salary, active) VALUES ('Active Dev', 'Engineering', 70000, TRUE)");
        jdbcTemplate.update("INSERT INTO employees (name, department, salary, active) VALUES ('Former Dev', 'Engineering', 60000, FALSE)");
    }
    @Test void archivedEndpointUsesTheQualifiedArchivedDao() {
        given().when().get("/employees/archived").then().statusCode(200).body("$", hasSize(1)).body("[0].name", equalTo("Former Dev")).body("active", everyItem(is(false)));
    }
}
