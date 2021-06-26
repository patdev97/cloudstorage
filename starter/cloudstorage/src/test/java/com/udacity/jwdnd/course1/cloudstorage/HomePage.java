package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    @FindBy(xpath = "//div[@id='logoutDiv']/form[1]/button[1]")
    private WebElement logoutButton;

    // Note elements
    @FindBy(id = "nav-notes-tab")
    public WebElement navNotesTab;

    @FindBy(xpath = "//div[@id='nav-notes']/button[1]")
    private WebElement buttonAddNewNote;

    @FindBy(id = "note-id")
    private WebElement noteId;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-submit")
    private WebElement noteSubmit;

    // Credentials elements

    @FindBy(id = "nav-credentials-tab")
    public WebElement navCredentialsTab;

    @FindBy(xpath = "//div[@id='nav-credentials']/button[1]")
    private WebElement buttonAddNewCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    // Constructor

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // General methods

    public void logout() {
        this.logoutButton.click();
    }

    private List<WebElement> getTableRows(WebDriver driver, String table) {
        WebElement noteTableBody = driver.findElement(By.id(table)).findElement(By.tagName("tbody"));
        return noteTableBody.findElements(By.tagName("tr"));
    }

    // Note methods

    public void navNoteAndAdd() throws InterruptedException {
        this.navNotesTab.click();
        Thread.sleep(1000);
        this.buttonAddNewNote.click();
        Thread.sleep(1000);
    }

    public void createNote(String title, String description) throws InterruptedException {
        this.noteTitle.clear();
        this.noteTitle.sendKeys(title);

        this.noteDescription.clear();
        this.noteDescription.sendKeys(description);

        this.noteSubmit.submit();
    }

    public List<String>[] getNotes(WebDriver driver) {
        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        List<WebElement> tableRows = this.getTableRows(driver, "noteTable");

        for(WebElement tr : tableRows) {
            WebElement title = tr.findElement(By.tagName("th"));
            WebElement description = tr.findElements(By.tagName("td")).get(1);
            titles.add(title.getText());
            descriptions.add(description.getText());
        }

        return new List[]{titles, descriptions};
    }

    public void clickEditNote(WebDriver driver, int id) {
        this.getTableRows(driver, "noteTable").get(id).findElement(By.tagName("td")).findElement(By.tagName("button")).click();
    }

    public void deleteNote(WebDriver driver, int id) {
        this.getTableRows(driver, "noteTable").get(id).findElement(By.tagName("td")).findElement(By.tagName("a")).click();
    }

    // Credential methods

    public void navCredentialsAndAdd() throws InterruptedException {
        this.navCredentialsTab.click();
        Thread.sleep(1000);
        this.buttonAddNewCredential.click();
        Thread.sleep(1000);
    }

    public void createCredential(String url, String username, String password) {
        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(url);

        this.credentialUsername.clear();
        this.credentialUsername.sendKeys(username);

        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(password);

        this.credentialSubmit.submit();
    }

    public List<WebElement[]> getCredentials(WebDriver driver) {
        List<WebElement[]> result = new ArrayList<>();
        for(WebElement tr : this.getTableRows(driver, "credentialTable")) {
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            WebElement editButton = tds.get(0).findElement(By.tagName("button"));
            WebElement deleteButton = tds.get(0).findElement(By.tagName("a"));
            WebElement url = tr.findElement(By.tagName("th"));
            WebElement username = tds.get(1);
            WebElement password = tds.get(2);

            WebElement[] credentials = new WebElement[] { editButton, deleteButton, url, username, password };
            result.add(credentials);
        }
        return result;
    }
}
