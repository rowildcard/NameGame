package utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class NGsetup {
	    
	public WebDriver driver;
	
	//	SetUp() - basic setup for Chrome webdriver
	public static WebDriver SetUp() {
	
		//	setup Chrome to open incognito
		//	Ensures new User session with each call to SetUp
		ChromeOptions options = new ChromeOptions();
		options.addArguments("-incognito");
        // options.setHeadless(true);
		
		ChromeOptions capabilities = new ChromeOptions();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		//	Launch Chrome 
		WebDriver driver = new ChromeDriver(capabilities);
		
		//	turn on implicit wait timer
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  
		
		//	navigate to Web App
		driver.get("http://www.ericrochester.com/name-game/");
		return driver;	
		
	}	// SetUp
	
}	//	class
