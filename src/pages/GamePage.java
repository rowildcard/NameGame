package pages;

import utilities.NGWaits;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

//	created 10.29.2018	Rhonda Oglesby

//	PAGE OBJECT
public class GamePage {
	public String sBeforeAnsClass  = "photo";
	public String sCorrectAnsClass = "correct";
	public String sWrongAnsClass   = "wrong";
	
	public By byHeader       = By.className("text-muted");
	public By byWhoIs        = By.id("name");
	public By byPhoto 	     = By.className(sBeforeAnsClass);
	public By byPhotoName    = By.className("name");
	public By byPhotoCorrect = By.className(sCorrectAnsClass);
	public By byPhotoWrong   = By.className(sWrongAnsClass);
	public By byTries        = By.className("attempts");
	public By byCorrect      = By.className("correct");
	public By byStreak       = By.className("streak");

	public WebDriver driver;
	public int iNumInLineup = 5;
	public NGWaits uWait;
	
	public GamePage(WebDriver driver) {
		this.driver = driver;
		uWait = new NGWaits(this.driver);
	}
	
	//
	//	ASSERT TESTS for PAGE OBJECT
	//
	//	HEADER -------------------------------------------------------	
	//	Verify the image for the name in the quiz appears in the lineup.
	public void verifyWhoIsAppearsInLineup() {
		System.out.println("---verifyWhoIsTitle");
		Assertion hAssert = new Assertion();
		
		uWait.waitForVisibilityAll(byPhoto);
		
		String sCorrectName  = getCorrectName();
		int iCorrectAnsIndex = getCorrectAnsIndex();
		List<WebElement> weLineup = getLineup();
		
		String sLineupName = weLineup.get(iCorrectAnsIndex).findElement(byPhotoName).getText();
		hAssert.assertEquals(sLineupName, sCorrectName);
	}	//	---
	
	//	HEADER -------------------------------------------------------	
	//	Verify the WhoIs name and colleague lineup all change in the next quiz.
	//
	//	iNumOfLineups determines how many fresh lineups should be tested after the original.
	//	e.g. if = 3, a total of 3 lineups/quizes will load. Compare 1 to 2 AND 2 to 3.
	//
	public void verifyWhoIsAndLineupChange(int inumoflineups) {
		System.out.println("---verifyWhoIsAndLineupChange");
		SoftAssert sAssert = new SoftAssert();
		String sFirstCorrectName = "";
		String sSecondCorrectName = "";
		int iFirstCorrectAnsIndex = 0;
		String[] sFirstLineupNames = new String[iNumInLineup];
		String[] sSecondLineupNames = new String[iNumInLineup];
		
		//	How many lineups to compare; minimum of 2
		inumoflineups = validateNumOf(inumoflineups, 2);
		uWait.waitForNumElements(byPhoto, iNumInLineup);
		
		//	Compare <iNumOfLineups> lineups.
		for (int x = 0; x < inumoflineups; x++) {
			List<WebElement> weFirstLineup = getLineup();
			sFirstCorrectName  = getCorrectName();
			iFirstCorrectAnsIndex = getCorrectAnsIndex();
			
			//	Save the names in the lineup
			for (int i = 0; i < iNumInLineup; i++) {
				sFirstLineupNames[i] = weFirstLineup.get(i).findElement(byPhotoName).getText();
			}  
			
			//	Click correct answer to reload a new quiz
			weFirstLineup.get(iFirstCorrectAnsIndex).click();
			
			//	Wait for new quiz lineup to load
			uWait.waitForNumElements(byPhoto, iNumInLineup);
			
			//	Get new WhoIs and Lineup
			sSecondCorrectName  = getCorrectName();
			List<WebElement> weSecondLineup = getLineup();
			
			//	Assert new WhoIs is != old WhoIs
			sAssert.assertNotEquals(sFirstCorrectName, sSecondCorrectName);
			
			//	Assert new Lineup is != old Lineup
			//	Save the names in the lineup
			for (int i = 0; i < iNumInLineup; i++) {
				sSecondLineupNames[i] = weSecondLineup.get(i).findElement(byPhotoName).getText();
			}
			
			for (int i = 0; i < iNumInLineup; i++) {
				sAssert.assertNotEquals(sFirstLineupNames[i], sSecondLineupNames[i], "   first and second equal");
			}

		}	//	for iNumOfLineups
		
		sAssert.assertAll();
	}	//	---
	
	//	COUNTER ------------------------------------------------------
	//  Verify tries counter.
	//		Test can be run for clicks = <iNumOfTries>. 
	//
	public void verifyCounterTriesAndCorrect(int inumoftries) {
		System.out.println("---verifyCounterTriesAndCorrect()");	
		Assertion hAssert = new Assertion();
		int[] iRandomX = getRandomIndexList(iNumInLineup);
		int iRX = 0;
		int iNumCorrect = 0;

		inumoftries = validateNumOf(inumoftries, 1);
		uWait.waitForNumElements(byPhoto, iNumInLineup);

		hAssert.assertEquals(Integer.parseInt(driver.findElement(byTries).getText()), 0);
		hAssert.assertEquals(Integer.parseInt(driver.findElement(byCorrect).getText()), 0);
		
		int iAnswerX = getCorrectAnsIndex();
		List<WebElement> weLineup = getLineup();
					
		for (int i = 0; i < inumoftries; i++) {
			weLineup.get(iRandomX[iRX]).click();
			try {
				//	If the random selection was the correct answer, wait for a new quiz to load.
				if (iRandomX[iRX] == iAnswerX) {
					System.out.println("   correct answer clicked");
					uWait.waitForRefresh(byPhotoCorrect, 1);
					iNumCorrect++;
					hAssert.assertEquals(Integer.parseInt(driver.findElement(byTries).getText()), i + 1);
					hAssert.assertEquals(Integer.parseInt(driver.findElement(byCorrect).getText()), iNumCorrect);
					uWait.waitForNumElements(byPhoto, iNumInLineup);
					hAssert.assertEquals(Integer.parseInt(driver.findElement(byTries).getText()), i + 1);
					hAssert.assertEquals(Integer.parseInt(driver.findElement(byCorrect).getText()), iNumCorrect);
					weLineup = driver.findElements(byPhoto);
					iAnswerX = getCorrectAnsIndex();
					iRandomX = getRandomIndexList(iNumInLineup);
					iRX = 0;
				}
				else {
					System.out.println("   wrong answer clicked");
					uWait.waitForRefresh(byPhotoWrong, (iRX + 1));
					hAssert.assertEquals(Integer.parseInt(driver.findElement(byTries).getText()), i + 1);
					iRX++; 
				}
			}
	        catch(StaleElementReferenceException  x) {
	        	System.out.println("   StaleElementRef exception = " + x);
				System.out.println("   Tries counter = " + Integer.parseInt(driver.findElement(byTries).getText()) + ", exp = " + (i + 1));
	        }
	        catch(NoSuchElementException x) {
	        	System.out.println("   NoSuchElement exception = " + x);
	        }
		}

	}	//	---
	
	//	COUNTER ------------------------------------------------------
	//	Verify the streak counter
	//		
	// Loop : click 0 - 4 (iwrongs) wrong answers 
	// Loop : click correct answers until clicks == c
	//        For each correct answer, hard-assert streak counter
	//
	public void verifyCounterStreak(int istreaklen, int iwrongs) {
		System.out.println("---verifyCounterStreak");
		Assertion  hAssert = new Assertion();
		SoftAssert sAssert = new SoftAssert();
		int iW = 0;	// count wrong clicks
		
		istreaklen = validateNumOf(istreaklen, 1);
		
		uWait.waitForVisibilityAll(byPhoto);
		hAssert.assertEquals(driver.findElement(byCorrect).getText(), "0");
		List<WebElement> weLineup = getLineup();
		int iCorrect = getCorrectAnsIndex();
		
		//	Make a series of wrong selections before starting a streak
		for (int i = 0; i < weLineup.size(); i++) {
			if (i != iCorrect) {
				weLineup.get(i).click();
				iW++;
				if (iW == iwrongs) { i = weLineup.size(); }
			}
		}
		weLineup.get(iCorrect).click();
		
		try {
			for (int j = 0; j < istreaklen; j++) {
				uWait.waitForVisibilityAll(byPhoto);
				int iAnswer = getCorrectAnsIndex();
				weLineup = getLineup();
				weLineup.get(iAnswer).click();
				sAssert.assertEquals(Integer.parseInt(driver.findElement(byTries).getText()), j+1);
			}
			uWait.waitForVisibilityAll(byPhoto);
			int iWrong = getWrongIndex(getCorrectAnsIndex());
			weLineup = getLineup();
			weLineup.get(iWrong).click();
			sAssert.assertEquals(Integer.parseInt(driver.findElement(byTries).getText()), 0);
		}
		catch (Exception e) {
			System.out.println("verifyCounterTries Exception = " + e);
		}
	}	//	---

	//	FREQUENCY ---------------------------------------------------
	//	Verify a wrong answer appears more frequently in subsequent
	//	lineups than a correct answer.
	//		isamples - Number of lineups to sample for wrong and correct answer
	//
	public void frequencyOfWrongVsCorrect(int isamples) {
		System.out.println("---frequencyOfWrongVsCorrect");	
		SoftAssert sAssert = new SoftAssert();
		float iWrongFreq = 0;
		float iCorrectFreq = 0;
		int iWrongCnt = 0;
		int iCorrectCnt = 0;

		isamples = validateNumOf(isamples, 10);
		
		uWait.waitForNumElements(byPhoto, iNumInLineup);
		
		//	Get a correct answer to sample against.
		int iCorrect = getCorrectAnsIndex();
		String sCorrectName = getCorrectName();
		List<WebElement> weLineup = getLineup();

		//	Get a wrong answer to sample against.
		int iWrong = getWrongIndex(iCorrect);
		String sWrongName = weLineup.get(iWrong).getText();

		weLineup.get(iWrong).click();
		weLineup.get(iCorrect).click();

		uWait.waitForNumElements(byPhoto, iNumInLineup);
		
		for (int i = 0; i < isamples; i++) {
			iCorrect = getCorrectAnsIndex();
			sCorrectName = getCorrectName();
			iWrong = getWrongIndex(iCorrect);	//	random wrong answer
			weLineup = driver.findElements(byPhoto);
			
			//	Is the wrong or correct name in the lineup?
			for (int j = 0; j < weLineup.size(); j++) 
			{
				if (sWrongName.equals(sCorrectName)) {
					
					//	TBD - reset correct and wrong name
					
					iWrongFreq = iWrongCnt/isamples;
					iCorrectFreq = iCorrectCnt/isamples;
					sAssert.assertTrue(iWrongFreq > iCorrectFreq);
					System.out.println("Frequency of Wrong Answer   = " + iWrongFreq);
					System.out.println("Frequency of Correct Answer = " + iCorrectFreq);
				}
				else {
					if (sWrongName.equals(weLineup.get(j).getText())) {
						iWrongCnt++;
					}
				}
				if (sCorrectName.equals(weLineup.get(j).getText())) {
					iCorrectCnt++;
				}
			}	//	go thru lineup
			weLineup.get(iWrong).click();
			weLineup.get(iCorrect).click();

			uWait.waitForNumElements(byPhoto, iNumInLineup);
		}	// samples
		iWrongFreq = iWrongCnt/isamples;
		iCorrectFreq = iCorrectCnt/isamples;
		sAssert.assertTrue(iWrongFreq > iCorrectFreq);
		System.out.println("Frequency of Wrong Answer   = " + iWrongFreq);
		System.out.println("Frequency of Correct Answer = " + iCorrectFreq);
		
		sAssert.assertAll();
		
	}	// ---
	
	//
	//	CLASS UTILITY METHODS ----------------------------------------
	//
	//	For the current quiz, return the name for the correct answer.
	//
	public String getCorrectName() {
		String sName = driver.findElement(byWhoIs).getText();
		return sName;
	}	//	---
	
	//
	//	For the current quiz, return the Index for the correct answer.
	//
	public int getCorrectAnsIndex() {
		String sAns = driver.findElement(byWhoIs).getAttribute("data-n");
		int iAns = Integer.parseInt(sAns);
		return iAns; 
	}	//	---

	//
	//	For the current quiz, return an Index for any wrong answer.
	//
	public int getWrongIndex(int icorrectindex) {
		if (icorrectindex == 0) { return 1; }
		if (icorrectindex == (iNumInLineup - 1) ) { return (iNumInLineup - 2); }
		return (icorrectindex - 1);
	}
	
	//
	//	For the current quiz, return a List of the Lineup elements.
	//
	public List<WebElement> getLineup() {	
		List<WebElement> lineup = driver.findElements(byPhoto);
		return (lineup);
	}	//	---
	
	//
	//	Return an integer array of unique random numbers from 0 - (isize - 1).
	//
	public int[] getRandomIndexList(int isize) {
		ArrayList<Integer> iOrdered = new ArrayList<>(isize);
		for (int i = 0; i < isize; i++) { iOrdered.add(i); }
		
		//	Shuffle the integer array
		int[] iRandom = new int[isize];
		for (int j = 0; j < isize; j++){
		    iRandom[j] = iOrdered.remove((int)(Math.random() * iOrdered.size()));
		}
		return iRandom;
	}	//	---
	
	//
	//	Verify input parameter <iparm> meets minimum requirements.
	//
	public int validateNumOf(int iparm, int imin) {
		if (iparm < imin) { 
			return imin; 
		}
		else {
			return iparm;
		}
	}	//	---
	
}	//	class
