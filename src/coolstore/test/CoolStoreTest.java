package coolstore.test;

import java.net.MalformedURLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import coolstore.page.CoolStorePage;



public class CoolStoreTest extends BaseTest{
	  
	private CoolStorePage store;
	
	@BeforeTest
	@Parameters({"browser"})
    public void setUp(String browser) throws MalformedURLException {
        super.setUp(browser);
        store = new CoolStorePage(driver);
	System.out.println("Tests on " + browser);

    }

    @Test
    public void allItemsDisplayedTest() throws InterruptedException {
        store.goToHomePage();
        System.out.println("Trying to get list of all items...");
        List<String> itemPrice = store.getListOfItems();
        itemPrice.stream()
                 .forEach(System.out::println);
        System.out.println("----------------------------");
        
        Assert.assertTrue(itemPrice.size()>0);
    }
    
    @Test
    public void sumOfPrices() throws InterruptedException {
    	store.goToHomePage();
    	System.out.println("Trying to calculate sum of items prices...");
    	double allPrices = store.getAllPrices();
    	System.out.println("Sum of prices: " + allPrices);
        System.out.println("----------------------------");
    	Assert.assertTrue(allPrices >= 0);
    }
    
    @Test
    public void addToCartTest() throws InterruptedException {
    	store.goToHomePage();
    	System.out.println("Trying to add all elements in the cart...");
    	Double expected = store.getAllPrices();
    	System.out.println("Expected: " + expected);
    	store.addAllItemsToCart();
    	store.goToCart();
    	System.out.println("Now you are on page" + driver.getCurrentUrl());
    	Double actual = store.getTotalCartAmount();
    	System.out.println("Actual: " + actual);
    	System.out.println("----------------------------");
    	Assert.assertEquals(actual, expected);    	
    }
    
    
}
