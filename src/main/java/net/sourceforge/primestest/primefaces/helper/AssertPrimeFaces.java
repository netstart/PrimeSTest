package net.sourceforge.primestest.primefaces.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AssertPrimeFaces {
	private final WebDriver driver;
	
	public AssertPrimeFaces(final WebDriver driver) {
		this.driver = driver;
	}
	
	protected boolean hasClass(final WebElement e, final String c) {
		return e.getAttribute("class") != null && e.getAttribute("class").contains(c);
	}
	
	protected Object executeJS(final String js, final Object... os) {
		return ((JavascriptExecutor) driver).executeScript(js, os);
	}
	
	protected Integer getAnimationQueueSizeBySelector(final String selector, final String queue) {
		return ((Long) executeJS("return $('" + selector + "').queue('" + queue + "').length;")).intValue();
	}
	
	protected Boolean anyAnimationInProgress(final String selector, final String queue) {
		return (Boolean) executeJS(" var q = $('" + selector + "').queue('" + queue + "'); return q.length && q[0] == 'inprogress';");
	}
	
	protected Boolean anyAnimationInProgress(final String selector) {
		return (Boolean) executeJS(" var q = $('" + selector + "').queue(); return q.length != 0 && q[0] == 'inprogress';");
	}
	
	/**
	 * Compares given css value before and after a delay time
	 * 
	 * @param WebElement
	 *            e : UI element to look for
	 * @param String
	 *            cssValue : Style property to compare
	 * @param boolean increasing : Should increase or decrease
	 * @param long interval : Time in milliseconds to look before and after
	 * 
	 */
	protected Boolean shouldElementAnimating(final WebElement e, final String cssValue, final boolean increasing, final long interval)
			throws InterruptedException {
		
		final String initial = e.getCssValue(cssValue);
		
		Thread.sleep(interval);
		
		final String after = e.getCssValue(cssValue);
		
		try {
			final double init = Double.parseDouble(initial.replaceAll("px", "")), last = Double.parseDouble(after.replaceAll("px", "")), dif = last - init;
			return dif != 0 && (dif < 0) ^ increasing;
		} catch (final NumberFormatException ex) {
			// No action. Try with string compare.
		}
		
		final int diff = initial.compareToIgnoreCase(after);
		
		return diff != 0 && ((diff > 0) ^ increasing);
	}
}
