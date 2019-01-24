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
	
	public void goTo() {
        this.driver.get("file:///home/sasha/coolstore/Red Hat Cool Store MSA.html");
        System.out.println("Browser launched and navigated to CoolStore page");
    }
	
	public List<String> getListOfItems() {
        
        return driver.findElements(By.cssSelector(".card-pf.card-pf-accented"))
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
		NumberFormat format = NumberFormat.getCurrencyInstance();
		Number number = 0;
		try {
			number = format.parse(price);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
        return number.toString();
	}
	
	/*public void addAllItemsToCart() {

		for (WebElement el : driver.findElements(By.cssSelector(".card-pf.card-pf-accented"))) {
			clickAddToCart();
		}
		
	}*/
	
	public Double sumOfAllPrices() {
		double sum = 0;
		for (WebElement el : driver.findElements(By.cssSelector(".card-pf.card-pf-accented"))) {
			sum += Double.parseDouble(getPrice(el));
		}
		Double truncatedDouble = BigDecimal.valueOf(sum)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		return truncatedDouble;
	}
	
	public void clickAddToCart() throws InterruptedException {
		List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(text(),'Add To Cart')]"));

		for(int i=0;i<buttons.size();i++){
		    buttons.get(i).click();
		    Thread.sleep(5000L);
			System.out.println("CLICKED");

		}
		//System.out.println(driver.findElements(By.xpath("//button[contains(text(),'Add To Cart')]")));
		/*for( WebElement e :driver.findElements(By.xpath("//button[contains(text(),'Add To Cart')]"))) {
			e.click();
			System.out.println("CLICKED");
		}*/
		
	}


}
