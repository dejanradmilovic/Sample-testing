package samplesite.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import samplesite.WebPage.*;
import samplesite.WebDriverManager.WebDriverSetup;

import java.time.Duration;


public class SmokeTest {
    public WebDriver driver;
    private String host = System.getProperty("host");
    private String userIdentity = System.getProperty("userIdentity");
    private String dashboardUrl = System.getProperty("dashboardUrl");
    private String userRole = System.getProperty("userRole");
    private String categoryStandardizerReportUrl = System.getProperty("categoryStandardizerReportUrl");
    private String singleCategoryMappingUrl = System.getProperty("singleCategoryMappingUrl");
    private String reportName = System.getProperty("reportName");
    private String expectedTitle;
    private LoginSite loginSite;
    private Dashboard dashboard;
    private CategoryStandardizerReportPage categoryStandardizerReportPage;
    private ReportOperations reportOperations;
    private CategoryStandardizerReport categoryStandardizerReport;
    private SingleCategoryMappingPage singleCategoryMappingPage;

    @Parameters({"browser"})

    @BeforeSuite(alwaysRun = true)
    public void openApp(String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        loginSite = new LoginSite(driver);
        dashboard = new Dashboard(driver);
        categoryStandardizerReportPage = new CategoryStandardizerReportPage(driver);
        reportOperations = new ReportOperations(driver);
        categoryStandardizerReport = new CategoryStandardizerReport(driver);
        singleCategoryMappingPage= new SingleCategoryMappingPage(driver);
        driver.navigate().to(host);
        loginSite.verifyLoginPage(host);
    }

    @BeforeTest(alwaysRun = true, groups = {"Positive", "Negative"}, description = "Test if the user is able to" + "Login into samplesite App.")
    public void loginTosamplesite() {
        // Basic login procedure
        loginSite.submitUsername();
        loginSite.submitPassword();
        loginSite.clickLoginButton();
        //Check redirect url, displayed username and user-role
        dashboard.verifyHomepage(dashboardUrl, userIdentity, userRole);
    }

    @Test(priority = 1, groups = {"Positive"}, description = "Test if the user is able to" + "Open the Category Standardizer Report Site")
    public void openCategoryStandardizerReportPage() {
        dashboard.openCategoryStandardizerReportPage();
        expectedTitle = "Create Category Standardizer Report";
        assert (categoryStandardizerReportPage.getTitle().contains(expectedTitle));
        Assert.assertEquals(driver.getCurrentUrl(), categoryStandardizerReportUrl);
    }

    @Test(priority = 2, groups = {"Positive"}, description = "Test if the user is able to" + "Generate a Category Standardizer Report with all required information submitted",
            suiteName = "Smoke Test")
    public void testAllFieldEnteredCategoryStandardizerReport() throws InterruptedException {
        driver.navigate().refresh();
        // Provides all necesarry inputs in order to generate a Category Standardizer report
        categoryStandardizerReportPage.enterReportName(reportName);
        categoryStandardizerReportPage.chooseInputData();
        categoryStandardizerReportPage.uploadList();
        categoryStandardizerReportPage.selectOutputCategorySystem();
        Thread.sleep(1000);
        categoryStandardizerReportPage.selectCategoryColumn();
        categoryStandardizerReportPage.selectMaxNumberOfReturnedCategories();
        Thread.sleep(2000);
        categoryStandardizerReportPage.generateReport();
        // The wait is in order to provide enough time for the automatic redirect of the created report splash page to engage
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    @Test(priority = 3, groups = {"Positive"}, description = "Verify that report is created")
    public void testIsReportCreated() throws InterruptedException {
        // Filters the reports by creator in order to find the requested report more easily
        reportOperations.sortReportsByCreator();
        // Verifies the requested report is created
        WebElement report = reportOperations.getCreatedReport(reportName);
        Assert.assertEquals(report.getText(), reportName, "Report is not created");
        Assert.assertEquals(driver.getCurrentUrl(),
                "https://demo.samplesite.com/queries?utf8=%E2%9C%93&query_search_name=&items_per_page=200&users%5B%5D=3652&bucket=&bucket=",
                "Wrong Url opened, report is not created");
    }

    @Test(priority = 4, groups = {"Positive"}, description = "Test if the user is able to" + "Open the created report")
    public void testCreatedReport() {
        reportOperations.openCreatedReport(reportName);
        // Verifies that the opened report is the one just created by the user
        String expectedTitle = reportName;
        Assert.assertEquals(categoryStandardizerReport.getPageTitle(), expectedTitle, "Wrong report opened!");

    }

    @Test(priority = 5, groups = {"Positive"}, description = "Verify that created report has all the necessary widgets")
    public void testWidgets() throws InterruptedException {
        // Assert that the report widgets are present
        Assert.assertEquals(categoryStandardizerReport.getAnalysisInfoWidgetTitle(), "Analysis Info", "The Analysis Info widget is missing!");
        Assert.assertTrue(categoryStandardizerReport.checkIfAnalysisInfoWidgetContentIsDisplayed(),"There is no Analysis Info widget content displayed!");
        Assert.assertEquals(categoryStandardizerReport.getAnalysisOfInputCategoriesWidgetTitle(), "Analysis of input categories", "Analysis of input categories widget is missing!");
        Assert.assertTrue(categoryStandardizerReport.checkIfInputCategoriesWidgetContentIsDisplayed(),"There is no Input Categories widget content displayed!");
        // Downloads and verifies the Created Report is downloaded on the Users PC.
        categoryStandardizerReport.downloadReport();
//      categoryStandardizerReport.checkAddConclusionFeatureOnAnalysisResults();
        driver.navigate().back();
    }

    @Test(priority = 6, groups = {"Positive"}, description = "Test if the user is able to" + "Open the Single Category Mapping Site")
    public void openSingleCategoryMappingPage() {
        dashboard.openSingleCategoryMappingPage();
        expectedTitle = "Single Category Mapping";
        // Asserts that the user is redirected to the right page
        assert (singleCategoryMappingPage.getTitle().contains(expectedTitle));
        Assert.assertEquals(driver.getCurrentUrl(), singleCategoryMappingUrl);
    }

    @Test(priority = 7, groups = {"Positive"}, description = "Test if the user is able to" + "Generate a Single Category Mapping result with all required information submitted",
            suiteName = "Smoke Test")
    public void testAllFieldEnteredSingleCategoryMapping() throws InterruptedException {
        driver.navigate().refresh();
        // Provides all necesarry inputs in order to generate a Single Category Mapping result
        singleCategoryMappingPage.enterInputCategory();
        singleCategoryMappingPage.selectOutputCategorySystem();
        singleCategoryMappingPage.showAdvancedOptionsAndPopulateFields();
        Thread.sleep(2000);
        singleCategoryMappingPage.generateReport();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        singleCategoryMappingPage.checkIfAnyResultIsDisplayed();
        singleCategoryMappingPage.addFeedback();
        Thread.sleep(2000);
        driver.navigate().refresh();
        singleCategoryMappingPage.openHistoryDropdownMenu();
        singleCategoryMappingPage.verifyHistory();
        driver.navigate().back();
    }

    @AfterTest(alwaysRun = true, description = "Delete created report")
    private void deleteCreatedReport() throws InterruptedException {
        // Clean-up
        driver.navigate().to("https://demo.samplesite.com/queries");
        reportOperations.sortReportsByCreator();
        reportOperations.deleteCreatedReport(reportName);
        //Verify that the report is successfully deleted
        assert (reportOperations.successfullyDeletedQuery().contains("Successfully deleted selected queries"));
    }

    @AfterTest(alwaysRun = true, description = "Test if the user is able to"+ " Logout out of samplesite App.")
    private void singOut() {
        dashboard.singOut();
        Assert.assertEquals(driver.getCurrentUrl(), host, "User not logged out!");
    }

    @AfterSuite(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }

}
