package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstName, String lastName, String username, String password) {
        this.inputFirstName.clear();
        this.inputFirstName.sendKeys(firstName);

        this.inputLastName.clear();
        this.inputLastName.sendKeys(lastName);

        this.inputUsername.clear();
        this.inputUsername.sendKeys(username);

        this.inputPassword.clear();
        this.inputPassword.sendKeys(password);

        this.inputPassword.submit();
    }
}
