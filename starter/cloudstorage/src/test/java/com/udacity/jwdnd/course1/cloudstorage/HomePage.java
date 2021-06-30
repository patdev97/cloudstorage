package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private JavascriptExecutor executor;

    // Constructor

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.executor = (JavascriptExecutor) driver;
    }

    // General methods

    private List<WebElement> getTableRows(WebDriver driver, String table) {
        WebElement noteTableBody = driver.findElement(By.id(table)).findElement(By.tagName("tbody"));
        return noteTableBody.findElements(By.tagName("tr"));
    }

    private void clickButton(String id) {
        WebElement webElement = driver.findElement(By.id(id));
        executor.executeScript("arguments[0].click();", webElement);
    }

    private String getValue(String id) {
        WebElement webElement = driver.findElement(By.id(id));
        return (String) executor.executeScript("return arguments[0].value;", webElement);
    }

    private void setValue(String id, String value) {
        WebElement webElement = driver.findElement(By.id(id));
        executor.executeScript("arguments[0].value='" + value + "';", webElement);
    }

    private List<WebElement> getWebElements(String id) {
        return driver.findElements(By.id(id));
    }

    // Logout method

    public void logout() {
        this.clickButton("logout-button");
    }

    // Note methods

    public void navToNotesTab() {
        this.clickButton("nav-notes-tab");
    }

    public void navNoteAndAdd() {
        this.navToNotesTab();
        this.clickButton("button-add-new-note");
    }

    public void createNote(String title, String description) {
        this.setValue("note-title", title);
        this.setValue("note-description", description);
        this.clickButton("note-save");
    }

    public List<String>[] getNotes() {
        List<String> titles = new ArrayList<>();
        for(WebElement temp : this.getWebElements("tableNoteTitle")) {
            titles.add(temp.getAttribute("innerHTML"));
        }
        List<String> descriptions = new ArrayList<>();
        for(WebElement temp : this.getWebElements("tableNoteDescription")) {
            descriptions.add(temp.getAttribute("innerHTML"));
        }
        return new List[] { titles, descriptions };
    }

    public void editNote(int id, String title, String description) {
        List<WebElement> noteEdits = this.getWebElements("tableNoteEdit");
        executor.executeScript("arguments[0].click();", noteEdits.get(id));
        this.createNote(title, description);
    }

    public void deleteNote(int id) {
        List<WebElement> noteDeletes = this.getWebElements("tableNoteDelete");
        executor.executeScript("arguments[0].click();", noteDeletes.get(id));
    }

    // Credential methods

    public void navToCredentialsTab() {
        this.clickButton("nav-credentials-tab");
    }

    public void navCredentialsAndAdd() {
        this.navToCredentialsTab();
        this.clickButton("button-add-new-credential");
    }

    public void createCredential(String url, String username, String password) {
        this.setValue("credential-url", url);
        this.setValue("credential-username", username);
        this.setValue("credential-password", password);
        this.clickButton("credential-save");
    }

    public List<String>[] getCredentials() {
        List<String> urls = new ArrayList<>();
        for(WebElement temp : this.getWebElements("tableCredUrl")) {
            urls.add(temp.getAttribute("innerHTML"));
        }
        List<String> usernames = new ArrayList<>();
        for(WebElement temp : this.getWebElements("tableCredUsername")) {
            usernames.add(temp.getAttribute("innerHTML"));
        }
        List<String> passwords = new ArrayList<>();
        for(WebElement temp : this.getWebElements("tableCredPassword")) {
            passwords.add(temp.getAttribute("innerHTML"));
        }
        return new List[] { urls, usernames, passwords };
    }

    public void editCredential(int id, String url, String username, String password) {
        List<WebElement> credEdits = this.getWebElements("tableCredEdit");
        executor.executeScript("arguments[0].click();", credEdits.get(id));
        this.createCredential(url, username, password);
    }

    public void deleteCredential(int id) {
        List<WebElement> credDeletes = this.getWebElements("tableCredDelete");
        executor.executeScript("arguments[0].click();", credDeletes.get(id));
    }
}
