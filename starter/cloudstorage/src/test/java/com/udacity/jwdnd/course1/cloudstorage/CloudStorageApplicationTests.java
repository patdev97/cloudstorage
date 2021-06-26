package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

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
	public void signupLoginHomeLogout() {
		this.signupAndLogin();

		Assertions.assertEquals("Home", driver.getTitle());

		//Thread.sleep(120000);

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(BASE_URL + this.port + "/home");

		Assertions.assertEquals("Login", driver.getTitle());

	}

	private void signupAndLogin() {
		driver.get(BASE_URL + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Hans", "Wurst", "testuser", "testpass");

		driver.get(BASE_URL + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("testuser", "testpass");
	}

	@Test
	public void createNote() throws InterruptedException {
		this.signupAndLogin();

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
	public void editNote() throws InterruptedException {
		this.signupAndLogin();

		HomePage homePage = new HomePage(driver);
		homePage.navNoteAndAdd();
		homePage.createNote("Test title", "Test Note. Wanting to know if it works.");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navNotesTab.click();

		Thread.sleep(1000);

		homePage.clickEditNote(driver, 0);

		Thread.sleep(200);

		homePage.createNote("New title", "And also a new description");

		resultPage.link.click();

		homePage.navNotesTab.click();

		Thread.sleep(1000);
		List[] notes = homePage.getNotes(driver);

		Assertions.assertEquals("New title", notes[0].get(0));
		Assertions.assertEquals("And also a new description", notes[1].get(0));
	}

	@Test
	public void deleteNote() throws InterruptedException {
		this.signupAndLogin();

		HomePage homePage = new HomePage(driver);
		homePage.navNoteAndAdd();
		homePage.createNote("Test title", "Test Note. Wanting to know if it works.");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.link.click();

		homePage.navNotesTab.click();

		Thread.sleep(1000);

		homePage.deleteNote(driver, 0);

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
	
	

}
