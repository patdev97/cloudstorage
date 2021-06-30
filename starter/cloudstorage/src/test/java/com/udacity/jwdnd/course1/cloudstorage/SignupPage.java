package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SignupPage {

    private WebDriver driver;
    private JavascriptExecutor executor;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.executor = (JavascriptExecutor) driver;
    }

    public void signup(String firstName, String lastName, String username, String password) {
        executor.executeScript("arguments[0].value='" + firstName + "';", driver.findElement(By.id("inputFirstName")));
        executor.executeScript("arguments[0].value='" + lastName + "';", driver.findElement(By.id("inputLastName")));
        executor.executeScript("arguments[0].value='" + username + "';", driver.findElement(By.id("inputUsername")));
        executor.executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.id("inputSubmit")));
    }
}
