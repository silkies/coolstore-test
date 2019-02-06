package coolstore.page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
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
        this.wait = new WebDriverWait(driver, 5000);
        this.action = new Actions(driver);
    }
	
	public void goToHomePage() {
        this.driver.get("http://web-ui-coolstore-prod-demo.apps.s-und-n.de");
        System.out.println("Browser launched and navigated to CoolStore page");
        //Thread.sleep(10000);

}
	
	public List<String> getListOfItems() {
		By xpath = By.xpath("//div[@ng-repeat='item in products']");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
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
        String price = element.findElement(By.xpath("//h1[@class='ng-binding']")).getText().trim();

        return title + " - " + price;
    }
	
	private String getPrice(WebElement element) {
        String price = element.findElement(By.xpath("//h1[@class='ng-binding']")).getText().trim();
		
		return price.substring(1);
	}
	
	
	public Double sumOfAllPrices() {
		double sum = 0;
		By xpath = By.xpath("//div[@ng-repeat='item in products']");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
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
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Supplier<List<WebElement>> fetchComponents = () -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath));
		List<WebElement> buttons = fetchComponents.get();
		
		//List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(text(),'Add To Cart')]"));

		for (WebElement e : buttons) {
			e.click();
			System.out.println("Add to cart CLICKED");
		}	
	}
	
	public void goToCart() {
		By xpath = By.xpath("//a[@ng-href='#/cart']");
		WebElement cart = new WebDriverWait(driver, 10)
				.until(ExpectedConditions.presenceOfElementLocated(xpath));
		cart.click();
		//driver.findElement(By.xpath("//a[@ng-href='#/cart']")).click();
		//Thread.sleep(10000);
		
	}
	
	public Double getTotalCartAmount() {
		double amount = -1;
		String cartTotal = driver.findElement(By.xpath("//h3[contains(text(), 'Cart Total')]")).getText().trim();
		amount = Double.parseDouble(cartTotal.substring(cartTotal.lastIndexOf("$") + 1));
		return amount;
		
	}


}
