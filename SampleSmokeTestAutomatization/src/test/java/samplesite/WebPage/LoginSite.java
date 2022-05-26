package samplesite.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginSite {
    private WebDriver driver;
    private String username = "valid_username@gmail.com";
    private String password = "CHYUq$U3";

    public LoginSite(WebDriver driver) {

        this.driver = driver;
    }

    public void verifyLoginPage(String host) {
        Assert.assertEquals(this.driver.getCurrentUrl(), host);
        Assert.assertEquals(this.driver.getTitle(), "samplesite");
        getLogo().isDisplayed();
    }

    private WebElement getLogo() {
        WebElement logo = this.driver.findElement(By.xpath("//div[@id= 'login']/img"));
        return logo;
    }

    public void submitUsername() {
        this.driver.findElement(By.id("email")).sendKeys(username);
    }

    public void submitPassword() {
                     this.driver.findElement(By.id("password")).sendKeys(password);
    }

    public void clickLoginButton() {
        this.driver.findElement(By.name("commit")).click();
    }


}
