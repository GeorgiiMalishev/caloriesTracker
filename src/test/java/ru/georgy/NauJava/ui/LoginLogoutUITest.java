package ru.georgy.NauJava.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.georgy.NauJava.model.Role;
import ru.georgy.NauJava.model.User;
import ru.georgy.NauJava.repository.UserRepository;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginLogoutUITest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private WebDriver driver;
    private String baseUrl;
    private WebDriverWait wait;

    private static final String LOGIN_URL = "/login";
    private static final String DEFAULT_SUCCESS_URL = "/products/list";
    private static final String LOGOUT_SUCCESS_URL = "/login?logout=true";

    private static final By USERNAME_INPUT = By.id("username");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
    private static final By LOGOUT_BUTTON = By.cssSelector("button.logout-btn");

    private static final String TEST_USER = "testuser";
    private static final String TEST_PASS = "password";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User testUser = new User();
        testUser.setUsername(TEST_USER);
        testUser.setPasswordHash(passwordEncoder.encode(TEST_PASS));
        testUser.setEmail("test@example.com");
        testUser.setRoles(Set.of(Role.USER));
        userRepository.save(testUser);

        driver = new ChromeDriver();
        baseUrl = "http://localhost:" + port;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get(baseUrl + LOGIN_URL);
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        WebElement passwordInput = driver.findElement(PASSWORD_INPUT);
        WebElement loginButton = driver.findElement(LOGIN_BUTTON);

        usernameInput.sendKeys(TEST_USER);
        passwordInput.sendKeys(TEST_PASS);
        loginButton.click();

        String expectedUrl = baseUrl + DEFAULT_SUCCESS_URL;
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL после входа не совпадает с ожидаемым.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_BUTTON));
        assertTrue(driver.findElement(LOGOUT_BUTTON).isDisplayed(), "Кнопка выхода не отображается после входа.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Проверяет успешный вход пользователя: открывает страницу логина, вводит данные, подтверждает переход на страницу продуктов и наличие кнопки выхода.
     */
    @Test
    @DisplayName("Успешный вход пользователя")
    void testSuccessfulLogin() {
        driver.get(baseUrl + LOGIN_URL);
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        WebElement passwordInput = driver.findElement(PASSWORD_INPUT);
        WebElement loginButton = driver.findElement(LOGIN_BUTTON);
        usernameInput.sendKeys(TEST_USER);
        passwordInput.sendKeys(TEST_PASS);
        loginButton.click();
        String expectedUrl = baseUrl + DEFAULT_SUCCESS_URL;
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL после входа не совпадает с ожидаемым.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_BUTTON));
        assertTrue(driver.findElement(LOGOUT_BUTTON).isDisplayed(), "Кнопка выхода не отображается после входа.");
    }

    /**
     * Проверяет успешный выход пользователя: выполняет логин, нажимает кнопку выхода, подтверждает возврат на страницу логина и отображение формы входа.
     */
    @Test
    @DisplayName("Успешный выход пользователя")
    void testSuccessfulLogout() {
        driver.get(baseUrl + LOGIN_URL);
        WebElement usernameLoginInput = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        usernameLoginInput.sendKeys(TEST_USER);
        driver.findElement(PASSWORD_INPUT).sendKeys(TEST_PASS);
        driver.findElement(LOGIN_BUTTON).click();
        wait.until(ExpectedConditions.urlToBe(baseUrl + DEFAULT_SUCCESS_URL));
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
        logoutButton.click();
        String expectedUrl = baseUrl + LOGOUT_SUCCESS_URL;
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL после выхода не совпадает с ожидаемым.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        assertTrue(driver.findElement(USERNAME_INPUT).isDisplayed(), "Поле для ввода имени пользователя не отображается после выхода.");
    }
} 