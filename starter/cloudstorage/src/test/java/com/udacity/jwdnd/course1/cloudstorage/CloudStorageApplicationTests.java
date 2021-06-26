package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	public void signupLoginHomeLogout() throws InterruptedException {
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
	public void createNote() throws InterruptedException {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navNoteAndAdd();
		homePage.createNote("Test title", "Test Note. Wanting to know if it works.");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navNotesTab.click();

		Thread.sleep(1000);
		List[] notes = homePage.getNotes(driver);

		Assertions.assertEquals("Test title", notes[0].get(0));
		Assertions.assertEquals("Test Note. Wanting to know if it works.", notes[1].get(0));
	}

	@Test
	@Order(4)
	public void editNote() throws InterruptedException {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navNotesTab.click();
		Thread.sleep(1000);
		homePage.clickEditNote(driver, 0);
		Thread.sleep(1000);

		homePage.createNote("New title", "And also a new description");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navNotesTab.click();
		Thread.sleep(1000);

		List[] notes = homePage.getNotes(driver);

		Assertions.assertEquals("New title", notes[0].get(0));
		Assertions.assertEquals("And also a new description", notes[1].get(0));
	}

	@Test
	@Order(5)
	public void deleteNote() throws InterruptedException {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navNotesTab.click();
		Thread.sleep(1000);

		homePage.deleteNote(driver, 0);

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navNotesTab.click();
		Thread.sleep(1000);

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
	public void createCredential() throws InterruptedException {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navCredentialsAndAdd();
		homePage.createCredential("google.com", "testuser", "pass");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navCredentialsTab.click();
		Thread.sleep(1000);

		List<WebElement[]> credentials = homePage.getCredentials(driver);

		Assertions.assertEquals("google.com", credentials.get(0)[2].getText());
		Assertions.assertEquals("testuser", credentials.get(0)[3].getText());
		Assertions.assertNotEquals("pass", credentials.get(0)[4].getText());
	}

	@Test
	@Order(7)
	public void editCredential() throws InterruptedException {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navCredentialsTab.click();
		Thread.sleep(1000);

		List<WebElement[]> credentials = homePage.getCredentials(driver);

		credentials.get(0)[0].click();
		Thread.sleep(1000);

		homePage.createCredential("bing.com", "newuser","newpass");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navCredentialsTab.click();
		Thread.sleep(1000);

		credentials = homePage.getCredentials(driver);

		Assertions.assertEquals("bing.com", credentials.get(0)[2].getText());
		Assertions.assertEquals("newuser", credentials.get(0)[3].getText());
		Assertions.assertNotEquals("newpass", credentials.get(0)[4].getText());
	}

	@Test
	@Order(8)
	public void deleteCredential() throws InterruptedException {
		this.signupAndLogin(true);

		HomePage homePage = new HomePage(driver);
		homePage.navCredentialsTab.click();
		Thread.sleep(1000);

		List<WebElement[]> credentials = homePage.getCredentials(driver);

		credentials.get(0)[1].click();

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navCredentialsTab.click();
		Thread.sleep(1000);

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
