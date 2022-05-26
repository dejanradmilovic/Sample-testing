package samplesite.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;

public class CategoryStandardizerReport {
    private WebDriver driver;
    String fileDownloadPath = "C:/Users/Dejan/Downloads/";

    public CategoryStandardizerReport(WebDriver driver) {
                this.driver = driver;
    }

    public String getPageTitle() {
        return this.driver.findElement(By.xpath("//*[@id=\"page-title-id\"]")).getText();
    }

    public String getAnalysisInfoWidgetTitle() {
        return this.driver.findElement(By.xpath("//*[@id=\"category_standardizer_analysis_info\"]/div[1]/h5/span")).getText();
    }

    public Boolean checkIfAnalysisInfoWidgetContentIsDisplayed() {
        return this.driver.findElement(By.xpath("//*[@id=\"category_standardizer_analysis_info\"]/div[4]")).isDisplayed();
    }

    public String getAnalysisOfInputCategoriesWidgetTitle() {
        return this.driver.findElement(By.xpath("//*[@id=\"category_standardizer_input_analysis\"]/div[1]/h5/span")).getText();
    }

    public Boolean checkIfInputCategoriesWidgetContentIsDisplayed() {
        return this.driver.findElement(By.xpath("//*[@id=\"category_standardizer_input_analysis\"]/div[4]")).isDisplayed();
    }

    public void checkAddConclusionFeatureOnAnalysisResults() {
        this.driver.findElement(By.xpath("///*[@id=\"category_standardizer_analysis_info\"]/div[1]/div/a")).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"category_standardizer_analysis_info\"]/div[1]/div/ul/li[1]/a"))).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"conclusion_area\"]"))).click();
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"conclusion_area\"]"))).sendKeys("Successful conclusion");
        new WebDriverWait(this.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"addConclusion_form\"]/div[2]/div/button[2]"))).click();
    }

    public void downloadReport() throws InterruptedException {
        this.driver.findElement(By.xpath("//*[@id=\"category_standardizer_report-detailed-btn\"]")).click();
        Thread.sleep(2000);
        this.driver.findElement(By.xpath("//*[@id=\"clusters-download-btn\"]")).click();
        Thread.sleep(5000);
        Assert.assertTrue(isFileDownloaded(fileDownloadPath, "Smoke_test_report_-_Category_Standardizer_Report_output.csv"), "Failed to download Expected document");
    }
    public boolean isFileDownloaded(String fileDownloadPath, String fileName) {
        boolean flag = false;
        File directory = new File(fileDownloadPath);
        File[] content = directory.listFiles();
        for (int i = 0; i < content.length; i++) {
            if (content[i].getName().equals(fileName))
                return flag=true;
        }
        return flag;
    }

}
