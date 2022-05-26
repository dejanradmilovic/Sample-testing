package samplesite.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CategoryStandardizerReportPage {
    private final WebDriver driver;

    public CategoryStandardizerReportPage(WebDriver driver) {

        this.driver = driver;
    }

    public String getTitle() {
        return this.driver.findElement(
                By.xpath("//*[@id=\"categorization_dialog\"]/div[1]")).getText();
    }

    public void enterReportName(String reportName)     {
        this.driver.findElement(
                By.xpath("//*[@id=\"upload_query_name\"]")).sendKeys(reportName);
    }

    public void chooseInputData() {
        this.driver.findElement(
                By.xpath("//*[@id=\"sourceData\"]/div[1]/div/button/span")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"sourceData\"]/div[1]/div/ul/li[1]/a/label"))).click();
    }

    public void uploadList() {
        WebElement addFile = driver.findElement(
                By.xpath("//*[@id=\"standardizer-browse-btn\"]"));
        addFile.sendKeys("C:/Users/Dejan/IdeaProjects/ContentClassificationModuleSmokeTest/src/test/resources/category_standardizer_sample.txt");
    }

    public void selectOutputCategorySystem() {
        this.driver.findElement(
                By.xpath("//*[@id=\"sourceData\"]/div[5]/div[1]/div/button/b")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"sourceData\"]/div[5]/div[1]/div/ul/li[2]/a/label"))).click();
    }

    public void selectCategoryColumn() {
        this.driver.findElement(
                By.xpath("//*[@id=\"sourceData\"]/div[5]/div[2]/div/button/span")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"sourceData\"]/div[5]/div[2]/div/ul/li/a/label"))).click();
    }

    public void selectMaxNumberOfReturnedCategories() {
        this.driver.findElement(
                By.xpath("//*[@id=\"limit\"]")).clear();
        new WebDriverWait(this.driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"limit\"]"))).sendKeys("5");
    }

    public void generateReport() {
        this.driver.findElement(
                By.xpath("//*[@id=\"selects_container\"]/button")).click();
            }



}
