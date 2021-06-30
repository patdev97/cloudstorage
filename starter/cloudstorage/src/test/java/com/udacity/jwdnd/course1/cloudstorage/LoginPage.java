package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private JavascriptExecutor executor;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.executor = (JavascriptExecutor) driver;
    }

    public void login(String username, String password) {
        executor.executeScript("arguments[0].value='" + username + "';", driver.findElement(By.id("inputUsername")));
        executor.executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.id("input-submit")));
    }
}
