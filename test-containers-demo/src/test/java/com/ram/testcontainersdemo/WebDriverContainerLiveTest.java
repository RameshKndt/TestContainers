package com.ram.testcontainersdemo;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class WebDriverContainerLiveTest {
	public BrowserWebDriverContainer chrome = null;
	@BeforeEach
    public void init() { 
    	  chrome = new BrowserWebDriverContainer()
            .withCapabilities(new ChromeOptions());
        chrome.start();
        
    }

    @Test
    public void whenNavigatedToPage_thenHeadingIsInThePage() {
    	RemoteWebDriver driver = chrome.getWebDriver();
        driver.get("http://example.com");
        String heading = driver.findElement(By.xpath("/html/body/div/h1"))
            .getText();
        assertEquals("Example Domain", heading);
        
    }
    
}
