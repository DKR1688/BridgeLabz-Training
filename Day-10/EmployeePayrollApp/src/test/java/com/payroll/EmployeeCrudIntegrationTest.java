package com.payroll;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeCrudIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static int employeeId;

    @BeforeEach
    void setUp() {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void createEmployee() {
        jdbcTemplate.update("DELETE FROM employees");

        employeeId = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", "Kavya Nair",
                        "department", "Engineering",
                        "salary", 75000.00))
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .body("name", equalTo("Kavya Nair"))
                .body("department", equalTo("Engineering"))
                .extract()
                .path("employeeId");
    }

    @Test
    @Order(2)
    void getAllEmployees() {
        given()
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].name", equalTo("Kavya Nair"));
    }

    @Test
    @Order(3)
    void getEmployeeById() {
        given()
                .when()
                .get("/employees/{id}", employeeId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Kavya Nair"))
                .body("employeeId", equalTo(employeeId));
    }

    @Test
    @Order(4)
    void updateEmployee() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", "Kavya Nair Updated",
                        "department", "HR",
                        "salary", 80000.00))
                .when()
                .put("/employees/{id}", employeeId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Kavya Nair Updated"))
                .body("department", equalTo("HR"));
    }

    @Test
    @Order(5)
    void deleteEmployee() {
        given()
                .when()
                .delete("/employees/{id}", employeeId)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void getDeletedEmployeeReturns404() {
        given()
                .when()
                .get("/employees/{id}", employeeId)
                .then()
                .statusCode(404);
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeArchivedIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.reset();
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = port;
        jdbcTemplate.update("DELETE FROM employees");
        jdbcTemplate.update(
                "INSERT INTO employees (name, department, salary, active) VALUES ('Active Dev', 'Engineering', 70000, TRUE)");
        jdbcTemplate.update(
                "INSERT INTO employees (name, department, salary, active) VALUES ('Former Dev', 'Engineering', 60000, FALSE)");
    }

    @Test
    void archivedEndpointUsesTheQualifiedArchivedDao() {
        given()
                .when()
                .get("/employees/archived")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].name", equalTo("Former Dev"))
                .body("active", everyItem(is(false)));
    }
}
