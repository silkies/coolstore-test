package coolstore.test;

import java.net.MalformedURLException;
import java.util.ArrayList;
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
        store.goToHomePage();


    }

    @Test (priority=2)
    public void allItemsDisplayedTest() throws InterruptedException {
        System.out.println("Trying to get list of all items...");
        List<String> itemPrice = store.getListOfItems();
        itemPrice.stream()
                 .forEach(System.out::println);
        System.out.println("----------------------------");
        
        Assert.assertTrue(itemPrice.size()>0);
    }
    
    @Test (priority=2)
    public void sumOfPrices() throws InterruptedException {
    	System.out.println("Trying to calculate sum of items prices...");
    	double allPrices = store.getSumOfPrices();
    	System.out.println("Sum of prices: " + allPrices);
        System.out.println("----------------------------");
    	Assert.assertTrue(allPrices >= 0);
    }
    
    
    @Test (priority=3)
    public void addToCartTest() throws InterruptedException {
    	System.out.println("Trying to add all elements in the cart...");
    	Double expected = store.getSumOfPrices();
    	System.out.println("Expected: " + expected);
    	store.addAllItemsToCart();
    	store.goToCart();
    	System.out.println("Now you are on page" + driver.getCurrentUrl());
    	Double actual = store.getTotalCartAmount();
    	System.out.println("Actual: " + actual);
    	System.out.println("----------------------------");
    	Assert.assertEquals(actual, expected);    	
    }
    
    @Test (priority=1)
    public void allItemsTitles() {
    	System.out.println("Titles by class");
    	ArrayList<String> titles = store.getAllTitles();
    	System.out.println("----------------------------");
    	Assert.assertTrue(titles.size() > 0);
    }
    
    @Test (priority=1)
    public void titlesByTag() {
    	System.out.println("Titles by tag");
    	ArrayList<String> titles = store.getTitlesByTag();
    	System.out.println("----------------------------");
    	Assert.assertTrue(titles.size() > 0);
    }
    
    
}
