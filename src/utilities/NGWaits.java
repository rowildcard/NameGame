package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NGWaits {

	public WebDriver driver;
	
	public NGWaits(WebDriver driver) {
		this.driver = driver;
	}
		
	//	CLASS METHODS
	//
	//	Wait for <byElement> to be visible
	public void waitForVisibility(By byElement) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byElement) );
	}	//	---
	  
	//	Wait for ALL <byElement>s to be visible
	public void waitForVisibilityAll(By byElement) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byElement) );
	}	//	---
	
	//	Wait for DOM refresh, condition: numberOfElementsToBe()
	public void waitForRefresh(By byElement, int n) {
		WebDriverWait wait = new WebDriverWait(driver, 7);
		wait.until(ExpectedConditions.refreshed
				  (ExpectedConditions.numberOfElementsToBe(byElement, n)));
	}	//	---
	
	//	Wait for number of elements <byElement> to be 'n'
	public void waitForNumElements(By byElement, int n) {
		WebDriverWait wait = new WebDriverWait(driver, 8);
		wait.until(ExpectedConditions.numberOfElementsToBe(byElement, n));
	}	//	---
	
	//	Wait until the class attribute for element <e> changes from <previousValue> 
	public void waitForAttributeToChange(WebElement e, String previousValue) {
        WebDriverWait wait = new WebDriverWait(driver, 6);
        try {
        	//	wait until the element's class attribute changes
	    	wait.until((d) -> {
	    		if (e.getAttribute("class").equals(previousValue)) {
	    			return null;
	    		}
	    		else { 
	    			return e; 
	    		}
	    	});
        }
        catch(StaleElementReferenceException  x) {
        	System.out.println("   Exspected exception = " + x);
        }
        catch(NoSuchElementException x) {
        	System.out.println("   Exspected exception = " + x);
        }
	}	//	---
}	//	class
