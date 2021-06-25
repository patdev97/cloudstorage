package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static final String BASE_URL = "http://localhost:";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void signupAndLogin() throws InterruptedException {
		driver.get(BASE_URL + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Hans", "Wurst", "testuser", "testpass");

		driver.get(BASE_URL + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("testuser", "testpass");

		Assertions.assertEquals("Home", driver.getTitle());

		Thread.sleep(120000);
	}

}
