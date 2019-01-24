package coolstore.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	 protected WebDriver driver;
	    
	    @BeforeTest
	    public void setUp() throws MalformedURLException {
	        
	    	String host = System.getProperty("seleniumHubHost");
	        String port = System.getProperty("port");
	        
	        String browser = System.getProperty("browser");
	        DesiredCapabilities dc = DesiredCapabilities.chrome();
	        if(browser.equals("firefox")) {
	        	dc = DesiredCapabilities.firefox();
	        }
	        
	        driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), dc);
	    	
	    }
	    
	    @AfterTest
	    public void tearDown() throws InterruptedException {
	        driver.quit();
	    }  

}
