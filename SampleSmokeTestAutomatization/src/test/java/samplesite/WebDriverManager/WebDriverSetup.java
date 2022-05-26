package samplesite.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WebDriverSetup {

        public static WebDriver getWebDriver(String browserName) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    return getChromeDriver();
                case "edge":
                    return getEdgeDriver();
                default:
                    throw new IllegalArgumentException("Match case not found for browser: "
                            + browserName);
            }
        }
        private static WebDriver getChromeDriver() {
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
        private static WebDriver getEdgeDriver() {
            io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        }
    }

