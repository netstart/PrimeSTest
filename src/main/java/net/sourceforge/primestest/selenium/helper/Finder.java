package net.sourceforge.primestest.selenium.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Finder {
	private final WebDriver driver;
	
	public Finder(final WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement findElement(final By by) {
		return driver.findElement(by);
	}
	
	public WebElement findElementById(final String id) {
		return findElement(By.id(id));
	}
	
	public WebElement findElementByLinkText(final String linkText) {
		return findElement(By.linkText(linkText));
	}
	
	public WebElement findElementByTag(final String tagName) {
		return findElement(By.tagName(tagName));
	}
	
	public WebElement findElementByName(final String elementName) {
		return findElement(By.name(elementName));
	}
	
	public WebElement findElementByClass(final String className) {
		return findElement(By.className(className));
	}
	
	public WebElement findElementByXpath(final String path) {
		return findElement(By.xpath(path));
	}
	
	public WebElement findElementByCssSelector(final String cssSelector) {
		return findElement(By.cssSelector(cssSelector));
	}
	
	public void selectVisibleTextById(final String idComponenteSelect, final String visibleText) {
		final Select selectRegional = findSelectById(idComponenteSelect);
		
		selectRegional.selectByVisibleText(visibleText);
	}
	
	public Select findSelectById(final String id) {
		return new Select(findElementById(id));
	}
	
	public List<WebElement> findElementsBy(final By by) {
		return driver.findElements(by);
	}
	
	public List<WebElement> findElementsById(final String elementId) {
		return findElementsBy(By.id(elementId));
	}
	
	public List<WebElement> findElementsByTag(final String tagName) {
		return findElementsBy(By.tagName(tagName));
	}
	
	public List<WebElement> findElementsByName(final String elementName) {
		return findElementsBy(By.name(elementName));
	}
	
	public List<WebElement> findElementsByClass(final String className) {
		return findElementsBy(By.className(className));
	}
	
	public List<WebElement> findElementsByXpath(final String path) {
		return findElementsBy(By.xpath(path));
	}
	
	public List<WebElement> findElementsBySelector(final String selector) {
		return findElementsBy(By.cssSelector(selector));
	}
	
	public WebElement findBySelector(final WebElement parent, final String selector) {
		return parent.findElement(By.cssSelector(selector));
	}
	
	public List<WebElement> findElementsBySelector(final WebElement parent, final String selector) {
		return parent.findElements(By.cssSelector(selector));
	}
	
}
