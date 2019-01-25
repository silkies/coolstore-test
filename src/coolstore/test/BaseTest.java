package coolstore.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	 protected WebDriver driver;
	    
	    @BeforeTest
	    public void setUp(String browser) throws MalformedURLException {
	        
	    	String host = System.getProperty("seleniumHubHost");
	        String port = System.getProperty("port");
	        
	        if(browser.equals("firefox")) {
	        	DesiredCapabilities dc = DesiredCapabilities.firefox();
		        driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), dc);

	        }
	        
	        if(browser.equals("chrome")) {
	        	DesiredCapabilities dc = DesiredCapabilities.chrome();
		        driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), dc);

	        }
		this.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	    }
	    
	    @AfterTest
	    public void tearDown() throws InterruptedException {
	        driver.quit();
	    }  

}
