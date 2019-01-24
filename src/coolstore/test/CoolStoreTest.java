package coolstore.test;

import coolstore.page.*;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;



public class CoolStoreTest extends BaseTest{
	  
	private CoolStorePage store;
	
	@BeforeTest
    public void setUp() throws MalformedURLException {
        super.setUp();
        store = new CoolStorePage(driver);
    }

    @Test
    public void allItemsDisplayedTest() {
        store.goTo();
        List<String> itemPrice = store.getListOfItems();
        itemPrice.stream()
                 .forEach(System.out::println);
        System.out.println("----------------------------");
        
        Assert.assertTrue(itemPrice.size()>0);
    }
    
    @Test
    public void sumOfAlPrices() {
    	store.goTo();
    	double allPrices = store.sumOfAllPrices();
    	System.out.println("Sum of prices: " + allPrices);
        System.out.println("----------------------------");
    	Assert.assertTrue(allPrices > 0);
    }
    
    @Test
    public void addToCartTest() throws InterruptedException {
    	store.goTo();
    	store.clickAddToCart();
    	
    }
    
    
}