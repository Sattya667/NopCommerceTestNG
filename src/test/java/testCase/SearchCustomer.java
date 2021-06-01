package testCase;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import objectRepo.AddcustomerPage;
import objectRepo.LoginPage;
import objectRepo.SearchCustomerPage;
import util.TestUtility;

public class SearchCustomer extends PrePostConditions{
	
	
	@DataProvider
	public Object[][] getDataSearchCustomer() {
		Object[][] data = TestUtility.getTestData("SearchCustomer");
		return data;
	}
	
	@Test(priority=1,dataProvider="getDataSearchCustomer")
	public void searchCustomerusingEmail(String email, String firstname, String lastname) throws InterruptedException {
		
		test = report.createTest("Search Customer - Email");

		driver.get("http://admin-demo.nopcommerce.com/login");
		lp = new LoginPage(driver);
		lp.setUserName("admin@yourstore.com");
		lp.setPassword("admin");
		lp.clickLogin();
		String title;
		title = driver.getTitle();
		Assert.assertEquals(title, "Dashboard / nopCommerce administration");
		
		addCustomer = new AddcustomerPage(driver);
		addCustomer.clickOnCustomersMenu();
		addCustomer.clickOnCustomersMenuItem();
		
		searchCustomer = new SearchCustomerPage(driver);
		searchCustomer.setEmail("victoria_victoria@nopCommerce.com");
		searchCustomer.clickSearch();
        Thread.sleep(3000);
        boolean status=searchCustomer.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
        
        test.createNode("Search Customer");
        
        Assert.assertEquals(true, status);
		
	}
	
	@Test(priority=2,dataProvider="getDataSearchCustomer")
	public void searchCustomerusingName(String email, String firstname, String lastname) {
		
		test = report.createTest("Search Customer - Name");
		
		driver.get("http://admin-demo.nopcommerce.com/login");
		lp = new LoginPage(driver);
		lp.setUserName("admin@yourstore.com");
		lp.setPassword("admin");
		lp.clickLogin();
		String title;
		title = driver.getTitle();
		Assert.assertEquals(title, "Dashboard / nopCommerce administration");
		
		addCustomer = new AddcustomerPage(driver);
		addCustomer.clickOnCustomersMenu();
		addCustomer.clickOnCustomersMenuItem();
		
		searchCustomer = new SearchCustomerPage(driver);
		
		searchCustomer.setFirstName("Victoria");
		searchCustomer.setLastName("Terces");
        boolean status=searchCustomer.searchCustomerByName("Victoria Terces");
        
        test.createNode("Search Customer");
        
        Assert.assertEquals(true, status);
		
	}
	
}
