package net.sourceforge.primestest.selenium.helper;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Asserter {
	
	private final WebDriver driver;
	
	private final Finder finder;
	
	private Waiter waiter;
	
	public Asserter(final WebDriver driver) {
		this.driver = driver;
		finder = new Finder(driver);
		waiter = new Waiter(driver);
	}
	
	public Asserter(final WebDriver driver, final Finder finder) {
		this.driver = driver;
		this.finder = finder;
	}
	
	public boolean isElementPresentById(final String id) {
		try {
			isElementPresent(By.id(id));
			return true;
		} catch (final NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isElementPresent(final By by) {
		try {
			finder.findElement(by);
			return true;
		} catch (final NoSuchElementException e) {
			return false;
		}
	}
	
	public Boolean whenExistElementValueEquals(final String id, final String value) {
		final Boolean exist = waiter.waitUntilElementExistsById(id);
		if (exist) {
			final WebElement element = finder.findElementById(id);
			return element.getText().equals(value) || element.getAttribute("value").toString().equals(value);
		}
		
		return false;
		
	}
}
