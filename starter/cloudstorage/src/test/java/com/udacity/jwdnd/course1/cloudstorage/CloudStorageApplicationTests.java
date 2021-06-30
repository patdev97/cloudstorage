package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	@Order(1)
	public void unauthorizedUser() {
		driver.get(BASE_URL + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(BASE_URL + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get(BASE_URL + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(BASE_URL + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void signupLoginHomeLogout() {
		this.signupAndLogin(false);

		Assertions.assertEquals("Home", driver.getTitle());

		//Thread.sleep(360000);

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(BASE_URL + this.port + "/home");

		Assertions.assertEquals("Login", driver.getTitle());
	}

	private void signupAndLogin(boolean loginOnly) {
		if(!loginOnly) {
			driver.get(BASE_URL + this.port + "/signup");
			SignupPage signupPage = new SignupPage(driver);
			signupPage.signup("Hans", "Wurst", "testuser", "testpass");
		}
		driver.get(BASE_URL + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("testuser", "testpass");
	}

	@Test
	@Order(3)
	public void createNote() {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navNoteAndAdd();
		homePage.createNote("Test title", "Test Note. Wanting to know if it works.");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickLink();

		homePage.navToNotesTab();

		List[] notes = homePage.getNotes();

		Assertions.assertEquals("Test title", notes[0].get(0));
		Assertions.assertEquals("Test Note. Wanting to know if it works.", notes[1].get(0));
	}

	@Test
	@Order(4)
	public void editNote() {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navToNotesTab();
		homePage.editNote(0, "New title", "And also a new description");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickLink();

		homePage.navToNotesTab();

		List<String>[] notes = homePage.getNotes();

		Assertions.assertEquals("New title", notes[0].get(0));
		Assertions.assertEquals("And also a new description", notes[1].get(0));
	}

	@Test
	@Order(5)
	public void deleteNote() {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navToNotesTab();

		homePage.deleteNote(0);

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickLink();

		homePage.navToNotesTab();

		boolean available;
		try {
			driver.findElement(By.id("noteTable"));
			available = true;
		} catch(NoSuchElementException e) {
			available = false;
		}

		Assertions.assertEquals(false, available);
	}

	@Test
	@Order(6)
	public void createCredential() {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navCredentialsAndAdd();
		homePage.createCredential("http://google.com", "testuser", "pass");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickLink();

		homePage.navToCredentialsTab();

		List<String>[] credentials = homePage.getCredentials();

		Assertions.assertEquals("http://google.com", credentials[0].get(0));
		Assertions.assertEquals("testuser", credentials[1].get(0));
		Assertions.assertNotEquals("pass", credentials[2].get(0));
	}

	@Test
	@Order(7)
	public void editCredential() {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navToCredentialsTab();

		homePage.editCredential(0, "http://bing.com", "newuser","newpass");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickLink();

		homePage.navToCredentialsTab();

		List<String>[] credentials = homePage.getCredentials();

		Assertions.assertEquals("http://bing.com", credentials[0].get(0));
		Assertions.assertEquals("newuser", credentials[1].get(0));
		Assertions.assertNotEquals("newpass", credentials[2].get(0));
	}

	@Test
	@Order(8)
	public void deleteCredential() {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navToCredentialsTab();

		homePage.deleteCredential(0);

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickLink();

		homePage.navToCredentialsTab();

		boolean available;
		try {
			driver.findElement(By.id("credentialTable"));
			available = true;
		} catch(NoSuchElementException e) {
			available = false;
		}

		Assertions.assertEquals(false, available);
	}

}
