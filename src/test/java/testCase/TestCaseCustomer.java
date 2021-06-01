package testCase;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import objectRepo.AddcustomerPage;
import objectRepo.LoginPage;
import objectRepo.SearchCustomerPage;
import util.TestUtility;

public class TestCaseCustomer extends PrePostConditions{
	
//	public WebDriver driver;
	/*
	 * public LoginPage lp; public AddcustomerPage addCustomer; public
	 * SearchCustomerPage searchCustomer;
	 */
	
	/*
	 * public ExtentHtmlReporter htmlReporter; public ExtentReports report; public
	 * ExtentTest test;
	 */
	
	
	/*
	 * @BeforeTest public void setUpReporting() {
	 * 
	 * htmlReporter = new
	 * ExtentHtmlReporter("./Test-output/ExtentReport/index.html");
	 * htmlReporter.config().setDocumentTitle("Automation Report");
	 * htmlReporter.config().setReportName("Regression");
	 * htmlReporter.config().setTheme(Theme.DARK); htmlReporter.loadConfig(
	 * "C:\\Users\\Sibu\\eclipse-workspace\\CustomerCreationTestNG\\src\\test\\resources\\extent-config.xml"
	 * );
	 * 
	 * report = new ExtentReports(); report.attachReporter(htmlReporter);
	 * 
	 * }
	 * 
	 * @AfterTest public void teardownReporting() { report.flush(); }
	 * 
	 * 
	 * 
	 * @BeforeMethod public void setUp() { WebDriverManager.chromedriver().setup();
	 * driver = new ChromeDriver();
	 * 
	 * driver.manage().deleteAllCookies(); driver.manage().window().maximize(); }
	 */
	@DataProvider
	public Object[][] getDataAddCustomer() {
		Object [][] data = TestUtility.getTestData("AddCustomer");
		return data;
	}
	
	@Test(priority=1)
	public void checkLogin() {
		
		test = report.createTest("checkLogin");
		
		driver.get("http://admin-demo.nopcommerce.com/login");
		lp = new LoginPage(driver);
		lp.setUserName("admin@yourstore.com");
		lp.setPassword("admin");
		lp.clickLogin();
		String title;
		title = driver.getTitle();
		Assert.assertEquals(title, "Dashboard / nopCommerce administration");
		
//		test = report.createTest("checkLogout");
		lp.clickLogout();
		title = driver.getTitle();
		Assert.assertEquals(title, "Your store. Login");
		
	}
	
	@Test(dataProvider="getDataAddCustomer" , dependsOnMethods = {"checkLogin"},priority=2)
	public void addCustomer(String email, String password,String role,
			String gender,String vendor, String firstname,String lastname,
			String dob,String companyName, String adminContent ) throws InterruptedException, IOException {
		
		test = report.createTest("AddCustomer");
		
		test.createNode("Peform login");
		
		driver.get("http://admin-demo.nopcommerce.com/login");
		lp = new LoginPage(driver);
		lp.setUserName("admin@yourstore.com");
		lp.setPassword("admin");
		lp.clickLogin();
		String title;
		title = driver.getTitle();
		Assert.assertEquals(title, "Dashboard / nopCommerce administration");
		
		String sspath = TestCaseCustomer.getEvidence(driver, "Login_AddCustomer");
		test.addScreenCaptureFromPath(sspath);
		
		
		test.createNode("Add Customer Start");
		
		addCustomer = new AddcustomerPage(driver);
		addCustomer.clickOnCustomersMenu();
		addCustomer.clickOnCustomersMenuItem();
		addCustomer.clickOnAddnew();
		
		Assert.assertEquals("Add a new customer / nopCommerce administration", addCustomer.getPageTitle());
		
		//String email = randomestring() + "@gmail.com";
		
		//String email = "abc@gmaill.com";
		
		test.createNode("provide customer details");
		
        addCustomer.setEmail(email);
        addCustomer.setPassword(password);
        // Registered - default
        // The customer cannot be in both 'Guests' and 'Registered' customer roles
        // Add the customer to 'Guests' or 'Registered' customer role
        addCustomer.setCustomerRoles(role);
        Thread.sleep(3000);

        addCustomer.setManagerOfVendor(vendor);
        addCustomer.setGender(gender);
        addCustomer.setFirstName(firstname);
        addCustomer.setLastName(lastname);
        addCustomer.setDob(dob); // Format: D/MM/YYY
        addCustomer.setCompanyName(companyName);
        addCustomer.setAdminContent(adminContent);
        
		sspath = TestCaseCustomer.getEvidence(driver, "CustomerDetails_AddCustomer");
		test.addScreenCaptureFromPath(sspath);
        
        test.createNode("click on save");
        
        addCustomer.clickOnSave();
        
		
		  Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
		  .contains("The new customer has been added successfully"
		  ),"Customer creation failed");
		 
        
		/*
		 * Assert.assertEquals(driver.findElement(By.tagName("body")).getText(),
		 * "The new customer has been added successfully","Customer creation failed");
		 */
		
	}
	
	
	/*
	 * @Test public void searchCustomerUsingName () {
	 * 
	 * System.out.println("Yest to Design");
	 * 
	 * }
	 * 
	 * @Test public void searchCustomerUsingEmail() {
	 * 
	 * System.out.println("Yest to Design");
	 * 
	 * }
	 */
	

	
	/*
	 * @AfterMethod public void tearDown(ITestResult result) throws IOException {
	 * 
	 * if(result.getStatus()==ITestResult.FAILURE) { test.log(Status.FAIL,
	 * "TEST CASE FAILED IS "+result.getName()); test.log(Status.FAIL,
	 * "TEST CASE FAILED IS "+result.getThrowable());
	 * 
	 * 
	 * }else if (result.getStatus()==ITestResult.SKIP) { test.log(Status.SKIP,
	 * "TEST CASE SKIP IS "+result.getName()); test.log(Status.SKIP,
	 * "TEST CASE SKIP IS "+result.getThrowable());
	 * 
	 * }else if (result.getStatus()==ITestResult.SUCCESS) { test.log(Status.PASS,
	 * "TEST CASE PASS IS "+result.getName());
	 * 
	 * 
	 * }
	 * 
	 * String sspath = TestCaseCustomer.getEvidence(driver, result.getName()); //
	 * System.out.println(sspath); test.addScreenCaptureFromPath(sspath);
	 * 
	 * driver.quit();
	 * 
	 * }
	 */
	
	
	
	/*
	 * public static String getEvidence(WebDriver driver,String ssName) throws
	 * IOException {
	 * 
	 * String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	 * TakesScreenshot ts = (TakesScreenshot) driver;
	 * 
	 * File source = ts.getScreenshotAs(OutputType.FILE);
	 * 
	 * String destination =
	 * System.getProperty("user.dir")+"\\test-output\\ss\\"+ssName+"_"+date+".png";
	 * 
	 * File finalDestination = new File(destination);
	 * 
	 * FileUtils.moveFile(source, finalDestination);
	 * 
	 * return destination; }
	 */
	

}
