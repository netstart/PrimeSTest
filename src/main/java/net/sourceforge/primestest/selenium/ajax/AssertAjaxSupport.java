package net.sourceforge.primestest.selenium.ajax;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AssertAjaxSupport {
	
	private final WebDriver driver;
	
	private final FindElementAjaxSupport finderAjaxSupport;
	
	public AssertAjaxSupport(final WebDriver driver) {
		this.driver = driver;
		finderAjaxSupport = new FindElementAjaxSupport(driver);
	}
	
	/**
	 * 
	 * http://www.thoughtworks-studios.com/twist/2.3/help/
	 * how_do_i_handle_ajax_in_selenium2.html
	 * Another approach is to use ExpectedCondition and WebDriverWait strategy.
	 * The code below waits for 20 seconds or till the element is available,
	 * whichever is the earliest.
	 * 
	 */
	public boolean isVisible(final String id) {
		try {
			final WebElement element = finderAjaxSupport.findElement(id);
			if (element != null) {
				return true;
			} else {
				return false;
			}
			
		} catch (final NoSuchElementException ex) {
			return false;
		}
		
	}
}
