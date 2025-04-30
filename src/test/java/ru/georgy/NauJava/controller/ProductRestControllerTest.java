package ru.georgy.NauJava.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.georgy.NauJava.model.Role;
import ru.georgy.NauJava.model.User;
import ru.georgy.NauJava.repository.UserRepository;
import ru.georgy.NauJava.service.product.ProductInput;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://localhost";
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        userRepository.deleteAll();

        User testUser = new User();
        testUser.setUsername(USER);
        testUser.setPasswordHash(passwordEncoder.encode(PASS));
        testUser.setEmail("test@example.com");
        testUser.setRoles(Set.of(Role.USER));
        userRepository.save(testUser);
    }

    private static final String USER = "testuser";
    private static final String PASS = "password";

    /**
     * Успешный запрос GET /api/products с корректными параметрами
     */
    @Test
    public void testGetProductsWithValidParamsSuccess() {
        given()
                .auth().preemptive().basic(USER, PASS)
                .param("minProteins", 5.0)
                .param("maxCalories", 500.0)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThanOrEqualTo(0));
    }
    
    /**
     * Успешное создание продукта POST /api/products с валидным телом
     */
    @Test
    public void testCreateProductValidSuccess() {
        ProductInput input = new ProductInput(
                "Автотестовый продукт",
                250.0,
                15.0,
                35.0,
                10.0
        );

        given()
                .auth().preemptive().basic(USER, PASS)
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/api/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Автотестовый продукт"))
                .body("calories", equalTo(250.0f))
                .body("proteins", equalTo(15.0f))
                .body("carbs", equalTo(35.0f))
                .body("fats", equalTo(10.0f));
    }

    /**
     * Ошибка при создании продукта с пустым именем
     */
    @Test
    public void testCreateProductEmptyNameBadRequest() {
        ProductInput input = new ProductInput(
                "",
                100.0,
                10.0,
                20.0,
                5.0
        );

        given()
                .auth().preemptive().basic(USER, PASS)
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/api/products")
                .then()
                .statusCode(400);
    }

    /**
     * Ошибка при создании продукта с отрицательным значением жиров
     */
    @Test
    public void testCreateProductNegativeFatsBadRequest() {
        ProductInput input = new ProductInput(
                "Продукт",
                100.0,
                10.0,
                20.0,
                -1.0
        );

        given()
                .auth().preemptive().basic(USER, PASS)
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/api/products")
                .then()
                .statusCode(400);
    }

    /**
     * Ошибка при создании продукта без авторизации
     */
    @Test
    public void testCreateProductNoAuthUnauthorized() {
        ProductInput input = new ProductInput(
                "Продукт",
                100.0,
                10.0,
                20.0,
                5.0
        );

        given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/api/products")
                .then()
                .statusCode(401);
    }
}
