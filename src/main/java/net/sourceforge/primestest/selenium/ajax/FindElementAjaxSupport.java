package net.sourceforge.primestest.selenium.ajax;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class FindElementAjaxSupport {
	
	private final WebDriver driver;
	
	public FindElementAjaxSupport(final WebDriver driver) {
		this.driver = driver;
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
	public WebElement findElement(final String id) {
		final Wait<WebDriver> wait = new WebDriverWait(driver, 20);
		final WebElement element = wait.until(visibilityOfElementLocated(By.id(id)));
		return element;
	}
	
	private ExpectedCondition<WebElement> visibilityOfElementLocated(final By by) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(final WebDriver driver) {
				final WebElement element = driver.findElement(by);
				return element.isDisplayed() ? element : null;
			}
		};
	}
	
	public WebElement findElementPredicateWait(final WebDriver driver, final String id) {
		final FluentWait<By> fluentWait = new FluentWait<By>(By.id(id));
		fluentWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		fluentWait.withTimeout(15, TimeUnit.SECONDS);
		fluentWait.until(new PredicateWait(driver));
		
		return driver.findElement(By.id(id));
	}
	
	private class PredicateWait implements Predicate<By> {
		
		private final WebDriver driver;
		
		public PredicateWait(final WebDriver driver) {
			this.driver = driver;
		}
		
		@Override
		public boolean apply(final By by) {
			try {
				return driver.findElement(by).isDisplayed();
			} catch (final NoSuchElementException ex) {
				return false;
			}
		}
	}
	
}
