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

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void createNote(String title, String description) throws InterruptedException {
        this.noteTitle.clear();
        this.noteTitle.sendKeys(title);

        this.noteDescription.clear();
        this.noteDescription.sendKeys(description);

        this.noteSubmit.submit();
    }

    public void navNoteAndAdd() throws InterruptedException {
        this.navNotesTab.click();
        Thread.sleep(1000);
        this.buttonAddNewNote.click();
        Thread.sleep(1000);
    }

    public List<String>[] getNotes(WebDriver driver) {
        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        List<WebElement> tableRows = this.getTableRows(driver);

        for(WebElement tr : tableRows) {
            WebElement title = tr.findElement(By.tagName("th"));
            WebElement description = tr.findElements(By.tagName("td")).get(1);
            titles.add(title.getText());
            descriptions.add(description.getText());
        }

        return new List[]{titles, descriptions};
    }

    public void clickEditNote(WebDriver driver, int id) {
        this.getTableRows(driver).get(id).findElement(By.tagName("td")).findElement(By.tagName("button")).click();
    }

    public void deleteNote(WebDriver driver, int id) {
        this.getTableRows(driver).get(id).findElement(By.tagName("td")).findElement(By.tagName("a")).click();
    }

    private List<WebElement> getTableRows(WebDriver driver) {
        WebElement noteTableBody = driver.findElement(By.id("noteTable")).findElement(By.tagName("tbody"));
        return noteTableBody.findElements(By.tagName("tr"));
    }
}
