package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    private WebDriver driver;
    private JavascriptExecutor executor;

    @FindBy(tagName = "a")
    public WebElement link;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.executor = (JavascriptExecutor) driver;
    }

    public void clickLink() {
        executor.executeScript("arguments[0].click();", this.link);
    }

}
