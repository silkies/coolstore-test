package coolstore.page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CoolStorePage {
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions action;

	public CoolStorePage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 100);
        this.action = new Actions(driver);
    }
	
	public void goToHomePage() {
        this.driver.get("http://web-ui.avogt-coolstore.svc:8080");
        System.out.println("Browser launched and navigated to CoolStore page");
        System.out.println("TITLE: " + driver.getTitle());
        System.out.println("page loaded: " + waitUntilLoaded());

        //Thread.sleep(3000);
        
	}
	
	private boolean waitUntilLoaded() {
		boolean loaded =((JavascriptExecutor) this.driver).executeScript("return document.readyState").equals("complete");
		//System.out.println("By xpath" + driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div/div[2]/div/div[2]/div[1]/h1")).getText().toString());
	    return loaded;
	}
	
	public List<String> getListOfItems() {
		By xpath = By.xpath("//div[@ng-repeat='item in products']");
		//WebElement welement = driver.findElement(By.cssSelector(".card-pf-title"));
		//System.out.println("TEST!!!" + welement.getText());
		Supplier<List<WebElement>> fetchComponents = () -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath));
		List<WebElement> listOfElements = fetchComponents.get();
		
		
		return listOfElements
                      .stream()
                      .map(this::getItemAndPrice)
                      .collect(Collectors.toList());

    }
	
	
	
	private String getItemAndPrice(WebElement element) {
		//item name
        String title = element.findElement(By.cssSelector(".card-pf-title")).getText().trim();
        //item price
        By priceSelector = By.cssSelector("div.col-xs-6 > h1:first-child");
        String price = element.findElement(priceSelector).getText().trim();

        return title + " - " + price;
    }
	
	
	
	public Double getSumOfPrices() {
		
		double sum = 0;
		By xpath = By.xpath("//h1[@class='ng-binding']");
		By cssSelector = By.cssSelector("div.col-xs-6 > h1:first-child");
		
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		Supplier<List<WebElement>> fetchComponents = () -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cssSelector));
		List<WebElement> allPrices = fetchComponents.get();
		
		for (WebElement e : allPrices) {
			String price = e.getText().trim().substring(1);
			sum += Double.parseDouble(price);
		}
		
		BigDecimal bd = new BigDecimal(Double.toString(sum));
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	private String getPrice(WebElement element) {
        String price = element.findElement(By.xpath("//h1[@class='ng-binding']")).getText().trim();
		
		return price.substring(1);
	}
	
	public Double sumOfAllPrices() {
		double sum = 0;
		By xpath = By.xpath("//div[@ng-repeat='item in products']");
		
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		Supplier<List<WebElement>> fetchComponents = () -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath));
		List<WebElement> listOfElements = fetchComponents.get();
		
				
		for (WebElement el : listOfElements) {
			sum += Double.parseDouble(getPrice(el));
		}

		Double truncatedDouble = BigDecimal.valueOf(sum)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		return truncatedDouble;
	}
	
	public void addAllItemsToCart() {
		By xpath = By.xpath("//button[contains(text(),'Add To Cart')]");
		
		Supplier<List<WebElement>> fetchComponents = () -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath));
		List<WebElement> buttons = fetchComponents.get();
		
		for (WebElement e : buttons) {
			e.click();
			System.out.println("Add to cart CLICKED");
		}	
	}
	
	public void goToCart() {
		By xpath = By.xpath("//a[@ng-href='#/cart']");
		WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
		cart.click();
		
	}
	
	public Double getTotalCartAmount() {
		double amount = -1;
		String cartTotal = driver.findElement(By.xpath("//h3[contains(text(), 'Cart Total')]")).getText().trim();
		amount = Double.parseDouble(cartTotal.substring(cartTotal.lastIndexOf("$") + 1));
		return amount;
		
	}
	
	public ArrayList<String> getAllTitles() {
		ArrayList<String> listOfTitles = new ArrayList<String>(); 
		By className = By.className("card-pf-title");
		
		// Supplier<List<WebElement>> fetchComponents = () -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(className));
		// List<WebElement> titles = fetchComponents.get();
		List<WebElement> titles = driver.findElements(className);
		
		for (WebElement e : titles) {
			listOfTitles.add(e.getText().trim());
			System.out.println(e.getText().trim());
		
		}
		
		return listOfTitles;
		
	}
	
	public ArrayList<String> getTitlesByTag() {
		ArrayList<String> listOfTitles = new ArrayList<String>(); 
		By tag = By.tagName("h2");
		
		Supplier<List<WebElement>> fetchComponents = () -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tag));
		List<WebElement> titles = fetchComponents.get();
		// List<WebElement> titles = driver.findElements(tag);
		
		for (WebElement e : titles) {
			listOfTitles.add(e.getText().trim());
			System.out.println(e.getText().trim());
		
		}
		
		return listOfTitles;
		
	}


}
