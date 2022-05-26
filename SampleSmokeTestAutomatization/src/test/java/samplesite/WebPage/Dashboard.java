package samplesite.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.time.Duration;


public class Dashboard {
    private WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();

    public Dashboard(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyHomepage(String dashboardUrl, String username, String userRole) {
        Assert.assertEquals(driver.getCurrentUrl(), dashboardUrl, "The user is redirected to the wrong homepage after login!");
        verifyUsername(username);
        verifyUserRole(userRole);
        softAssert.assertAll();
    }

    private void verifyUsername(final String username) {
        WebElement user = this.driver.findElement(By.id("user-name"));
        Assert.assertEquals(user.getText(), "Dejan Radmilo... ", "Wrong user identity displayed!");
    }

    private void verifyUserRole(final String userRole) {
        WebElement role = this.driver.findElement(By.id("user-role"));
        softAssert.assertEquals(role.getText(), "Group Admin", "Wrong user role displayed!");
    }

    public void openCategoryStandardizerReportPage() {
        this.driver.findElement(By.xpath("//a[@class='dropdown-toggle create-dropdown']")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"categorystandardizer\"]/a/label"))).click();
    }

    public void openSingleCategoryMappingPage() {
        this.driver.findElement(By.xpath("//a[@class='dropdown-toggle create-dropdown']")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"singlecategorymapping\"]/a/label"))).click();
    }
    public void singOut() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.driver.findElement(By.xpath("//*[@id=\"user-name\"]")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[1]/div/div/ul[2]/li/ul/li[4]/a"))).click();

    }

   }
