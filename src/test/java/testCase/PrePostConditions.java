package testCase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import objectRepo.AddcustomerPage;
import objectRepo.LoginPage;
import objectRepo.SearchCustomerPage;

public class PrePostConditions {
	
	public WebDriver driver;
	
	public LoginPage lp;
	public AddcustomerPage addCustomer;
	public SearchCustomerPage searchCustomer;
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports report;
	public ExtentTest test;
	
	@BeforeTest
	public void setUpReporting() {
		
		htmlReporter = new ExtentHtmlReporter("./Test-output/ExtentReport/index.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Regression");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.loadConfig("C:\\Users\\Sibu\\eclipse-workspace\\CustomerCreationTestNG\\src\\test\\resources\\extent-config.xml");
		
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		
	}
	
	@AfterTest
	public void teardownReporting() {
		report.flush();
	}
	
	
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		 
		 driver.manage().deleteAllCookies();
		 driver.manage().window().maximize();
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED IS "+result.getName());
			test.log(Status.FAIL, "TEST CASE FAILED IS "+result.getThrowable());
			
			
		}else if (result.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP, "TEST CASE SKIP IS "+result.getName());
			test.log(Status.SKIP, "TEST CASE SKIP IS "+result.getThrowable());
			
		}else if (result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS, "TEST CASE PASS IS "+result.getName());
			
			
		}
		
		String sspath = TestCaseCustomer.getEvidence(driver, result.getName());
//		System.out.println(sspath);
		test.addScreenCaptureFromPath(sspath);
		
		driver.quit();
		
	}
	
	public static String getEvidence(WebDriver driver,String ssName) throws IOException {
		
		String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		String destination = System.getProperty("user.dir")+"\\test-output\\ss\\"+ssName+"_"+date+".png";
		
		File finalDestination = new File(destination);
		
		FileUtils.moveFile(source, finalDestination);
				
		return destination;
	}
	
public String doLogin() {
		
			
		driver.get("http://admin-demo.nopcommerce.com/login");
		lp = new LoginPage(driver);
		lp.setUserName("admin@yourstore.com");
		lp.setPassword("admin");
		lp.clickLogin();
		String title;
		title = driver.getTitle();
		//Assert.assertEquals(title, "Dashboard / nopCommerce administration");
		return title;
		
	}
	

}
