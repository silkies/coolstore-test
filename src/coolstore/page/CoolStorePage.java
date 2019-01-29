package coolstore.page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
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
	
	public void goToHomePage() throws InterruptedException {
        this.driver.get("http://web-ui-coolstore-prod-demo.apps.s-und-n.de");
        System.out.println("Browser launched and navigated to CoolStore page");
        Thread.sleep(10000);

}
	
	public List<String> getListOfItems() throws InterruptedException {
		//Thread.sleep(3000);
		return driver.findElements(By.xpath("//div[@ng-repeat='item in products']"))
                      .stream()
                      .map(this::getItemAndPrice)
                      .collect(Collectors.toList());

    }
	
	
	
	private String getItemAndPrice(WebElement element) {
		//item name
        String title = element.findElement(By.cssSelector(".card-pf-title")).getText().trim();
        //item price
        String price = element.findElement(By.cssSelector("html body div.ng-scope div.container.container-cards-pf.ng-scope div.row div.col-md-4.item.ng-scope.ng-isolate-scope div.card-pf.card-pf-accented div.card-pf-body div div.row.ng-scope div.col-xs-6 h1.ng-binding")).getText().trim();

        return title + " - " + price;
    }
	
	private String getPrice(WebElement element) {
        String price = element.findElement(By.cssSelector("html body div.ng-scope div.container.container-cards-pf.ng-scope div.row div.col-md-4.item.ng-scope.ng-isolate-scope div.card-pf.card-pf-accented div.card-pf-body div div.row.ng-scope div.col-xs-6 h1.ng-binding")).getText().trim();
		/*NumberFormat format = NumberFormat.getCurrencyInstance();
		Number number = 0;
		System.out.println("Not converted price" + price);
		try {
			number = format.parse(price);
		} catch (ParseException e) {
			System.out.println("couldnt convert dollar sign!!!");;
		}*/
		return price.substring(1);
	}
	
	
	public Double sumOfAllPrices() throws InterruptedException {
		double sum = 0;
				
		for (WebElement el : driver.findElements(By.xpath("//div[@ng-repeat='item in products']"))) {
			sum += Double.parseDouble(getPrice(el));
		}
		Thread.sleep(3000);
		Double truncatedDouble = BigDecimal.valueOf(sum)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		return truncatedDouble;
	}
	
	public void addAllItemsToCart() throws InterruptedException {
		Thread.sleep(3000);
		List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(text(),'Add To Cart')]"));

		for(int i=0;i<buttons.size();i++){
		    buttons.get(i).click();
			System.out.println("Add to cart CLICKED");
			Thread.sleep(3000);
		}
		
		
		
	}
	
	public void goToCart() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@ng-href='#/cart']")).click();
		Thread.sleep(10000);
		
	}
	
	public Double getTotalCartAmount() {
		double amount = -1;
		String cartTotal = driver.findElement(By.xpath("//h3[contains(text(), 'Cart Total')]")).getText().trim();
		amount = Double.parseDouble(cartTotal.substring(cartTotal.lastIndexOf("$") + 1));
		return amount;
		
	}


}
