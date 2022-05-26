package samplesite.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReportOperations {
    private WebDriver driver;

    public ReportOperations(WebDriver driver) {
                        this.driver = driver;
    }

    public void sortReportsByCreator() throws InterruptedException {
        this.driver.findElement(
                By.xpath("//*[@id=\"filters-table\"]/tbody/tr/td[5]/div/button/span")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"filters-table\"]/tbody/tr/td[5]/div/ul/div/li[8]/a/label"))).click();
        Thread.sleep(1500);
    }

    public WebElement getCreatedReport(String reportName) {
        String path = "//div[@class='query_name']/a[text()='" + reportName + "']";
        return this.driver.findElement(By.xpath(path));
    }

    public void openCreatedReport(String reportName) {
        WebElement createdReport = getCreatedReport(reportName);
        new WebDriverWait(this.driver, Duration.ofSeconds(480)).until(ExpectedConditions.elementToBeClickable(createdReport)).click();
    }

    private void checkCreatedReport(String reportName) throws InterruptedException {
        String checkboxValue = findValueOfCreatedReport(reportName);
        String path = "//*[@name='queries' and @value='" + checkboxValue + "']/./..";
        this.driver.findElement(By.xpath(path)).click();
        }


    private String findValueOfCreatedReport(String reportName) {
        String path = "//div[@class='query_name']/a[text()='" + reportName + "']";
        String id = this.driver.findElement(By.xpath(path)).getAttribute("id");
        String value = id.substring(8);
        return value;
    }

    public void deleteCreatedReport(String reportName) throws InterruptedException {
        checkCreatedReport(reportName);
        this.driver.findElement(By.id("action-delete")).click();
        confirmDelete();
    }

    private void confirmDelete() {
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("confirm-link"))).click();
    }

    public String successfullyDeletedQuery() {

        return this.driver.findElement(By.id("alert-place")).getText();
    }
    public String captureRequestID() {
        String currentUrl = this.driver.getCurrentUrl();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(currentUrl);
        String id = null;
        while (matcher.find()) {
            id = (matcher.group());
        }
        return id;
    }

}
