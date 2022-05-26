package samplesite.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;


public class SingleCategoryMappingPage {
    private final WebDriver driver;
    public SingleCategoryMappingPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getTitle() {
        return this.driver.findElement(
                By.xpath("//*[@id=\"categorization_sampler\"]/div[1]/div")).getText();
    }

    public void enterInputCategory()     {
        this.driver.findElement(
                By.xpath("//*[@id=\"category\"]")).sendKeys("fashion");
    }

    public void selectOutputCategorySystem() {
        this.driver.findElement(
                By.xpath("//*[@id=\"sampler_form\"]/div/div[2]/div/button/span")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"sampler_form\"]/div/div[2]/div/ul/li[2]/a/label"))).click();
    }
    public void showAdvancedOptionsAndPopulateFields() {
        // Show Advanced options
        this.driver.findElement(
                By.xpath("//*[@id=\"show_more\"]/i")).click();
        //Clear the Number of results field
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"limit\"]"))).clear();
        // Populate the Number of results field
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"limit\"]"))).sendKeys("2");
        // Clear the Threshold cutoff field
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"threshold\"]"))).clear();
        // Populate the Threshold cutoff field
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"threshold\"]"))).sendKeys("0.5");
    }
    public void generateReport() {
        // Click on the Map button
        this.driver.findElement(
                By.xpath("//*[@id=\"sampler_form\"]/div/div[3]/button")).click();
            }
    public Boolean checkIfAnyResultIsDisplayed() {
        return this.driver.findElement(By.xpath("//*[@id=\"output_cat\"]")).isDisplayed();
    }
    public void openHistoryDropdownMenu(){
        this.driver.findElement(
                By.xpath("//*[@id=\"history-label\"]")).click();
    }
    public void verifyHistory() {
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"history-table\"]/tbody/tr[2]/td[1]")).getText(), "fashion", "Wrong Input category displayed");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"history-table\"]/tbody/tr[2]/td[3]")).getText(), "samplesite", "Wrong output system displayed");
    }

    public void addFeedback() throws InterruptedException {
        this.driver.findElement(
                By.xpath("//*[@id=\"feedback-btn\"]")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"feedback-inquiry\"]/p[2]/div/button/span"))).click();
                new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"feedback-inquiry\"]/p[2]/div/ul/li/div/input"))).sendKeys("clothing");
        this.driver.findElement(
                By.xpath("//*[@id=\"feedback-inquiry\"]/p[2]/div/ul/div/li[57]/a/label")).click();
        Thread.sleep(2000);
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"feedback-inquiry\"]/p[2]/button"))).click();
    }
}
