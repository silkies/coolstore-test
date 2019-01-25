package coolstore.test;

import java.net.MalformedURLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import coolstore.page.CoolStorePage;



public class CoolStoreTest extends BaseTest{
	  
	private CoolStorePage store;
	
	@BeforeTest
    public void setUp() throws MalformedURLException {
        super.setUp();
        store = new CoolStorePage(driver);
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
    public void sumOfAllPrices() throws InterruptedException {
    	store.goToHomePage();
    	System.out.println("Trying to calculate sum of all prices...");
    	double allPrices = store.sumOfAllPrices();
    	System.out.println("Sum of prices: " + allPrices);
        System.out.println("----------------------------");
    	Assert.assertTrue(allPrices >= 0);
    }
    
    @Test
    public void addToCartTest() throws InterruptedException {
    	store.goToHomePage();
    	System.out.println("Trying to add all elements in the cart...");
    	Double expected = store.sumOfAllPrices();
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
