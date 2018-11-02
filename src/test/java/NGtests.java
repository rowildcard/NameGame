package test.java;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
  
import pages.GamePage;
import utilities.NGsetup;
  
import org.openqa.selenium.WebDriver;

//	Name Game Assessment 10.29.2018

public class NGtests {
	
	static WebDriver driver;

	//	Setting up a Web Driver before each test will reset the User session.
	//	i.e. all counters return to 0
	//	Before each @Test --------------------------
	@BeforeMethod
	public static void BeforeTests() {
		driver = NGsetup.SetUp();
	}
	
	//	After each @Test ---------------------------	
	@AfterMethod
	public static void AfterTests() {
		driver.quit();
	}
	//
	//	Verify "streak" increments on "correct" selections
	//	Verify "streak" is reset on an incorrect selection
	//	Verify that after 10 random selections the counters are incremented correctly
	//	Verify "name" and lineup photos change after a correct answer is selected
	//	Colleagues that become "phone wrong" appear more frequently in lineup than Colleagues that become "photo correct"
	
	@Test
	//	--- DONE
	public static void verifyWhoIsAppearsInLineup() {
		new GamePage(driver).verifyWhoIsAppearsInLineup();
	}
	
	@Test 
	//	--- Errors
	public static void verifyWhoIsAndLineupChange() {
		new GamePage(driver).verifyWhoIsAndLineupChange(3);
	}
	
	@Test 
	//	--- DONE but assertions fail randomly on wrong clicks and sometimes Stale Element Exception
	public static void verifyCounterTriesAndCorrect() {
		new GamePage(driver).verifyCounterTriesAndCorrect(5);
	}
	
	@Test
	//	--- DONE
	public static void verifyCounterStreak() {
		new GamePage(driver).verifyCounterStreak(3, 1);
	}
	
	@Test	
	//	--- MOSTLY DONE
	public static void frequencyOfWrongVsCorrect() {
		new GamePage(driver).frequencyOfWrongVsCorrect(13); //	The sample size should be >= number of employees
	}
	
}	//	class