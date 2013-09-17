package net.sourceforge.primestest.selenium.helper;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * http://code.google.com/p/primefaces/source/browse/examples/trunk/showcase/src /test/java/com/prime/showcase/integration/SeleniumActionHelper.java?r=6618
 * 
 */
public class Actioner {
	
	private static Actions action;
	
	private final Finder finder;
	
	private final WebDriver driver;
	
	private final Waiter waiter;
	
	public Actioner(final WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
		finder = new Finder(driver);
		waiter = new Waiter(driver);
	}
	
	public Actioner(final WebDriver driver, final Finder finder) {
		this.finder = finder;
		this.driver = driver;
		
		action = new Actions(driver);
		waiter = new Waiter(driver);
	}
	
	public void rightClick(final WebElement body) {
		action.contextClick(body).build().perform();
	}
	
	public void mouseHoverByLinkText(final String linkText) {
		final WebElement element = finder.findElementByLinkText(linkText);
		mouseHover(element);
	}
	
	public void mouseHoverById(final String id) {
		final WebElement element = finder.findElementById(id);
		mouseHover(element);
	}
	
	public void mouseHover(final WebElement element) {
		action.moveToElement(element).build().perform();
	}
	
	public void dndByOffset(final WebElement element, final int x, final int y) {
		action.dragAndDropBy(element, x, y).build().perform();
	}
	
	public void dndToElement(final WebElement source, final WebElement target) {
		action.dragAndDrop(source, target).build().perform();
	}
	
	public void keyDown(final WebElement e, final Keys keys) {
		action.keyDown(e, keys).build().perform();
	}
	
	public void keyDown(final Keys keys) {
		action.keyDown(keys).build().perform();
	}
	
	public void keyUp(final WebElement e, final Keys keys) {
		action.keyUp(e, keys).build().perform();
	}
	
	public void keyUp(final Keys keys) {
		action.keyUp(keys).build().perform();
	}
	
	public void clickOnCurrentPosition() {
		action.click().perform();
	}
	
	public void clickAndHoldOnElement(final WebElement element) {
		action.clickAndHold(element).perform();
	}
	
	public void clickElementById(final String elementId) {
		finder.findElementById(elementId).click();
	}
	
	public void clickElementByLinkText(final String linkText) {
		finder.findElementByLinkText(linkText).click();
	}
	
	public void dblClick(final WebElement element) {
		action.doubleClick(element).perform();
	}
	
	public void moveByOffSet(final int x, final int y) {
		action.moveByOffset(x, y).perform();
	}
	
	public void releaseMouse() {
		action.release().perform();
	}
	
	public void selectElementByValue(final String id, final String value) {
		finder.findSelectById(id).selectByVisibleText(value);
	}
	
	public void selectElementByValue(final WebElement element, final String value) {
		new Select(element).selectByVisibleText(value);
	}
	
	public WebElement writeInInputText(final String inputText, final String valor) {
		final WebElement webElement = finder.findElementByName(inputText);
		webElement.clear();
		webElement.sendKeys(valor);
		return webElement;
	}
	
	public void navigateTo(final String url) {
		driver.navigate().to(url);
	}
	
	public void clearById(final String id) {
		finder.findElementById(id).clear();
	}
	
	public void sendKeysBydId(final String id, final String keys) {
		finder.findElementById(id).sendKeys(keys);
	}
	
	public void clickWhenIsDisplayed(final String id) {
		waiter.waitIsDisplayed(id, Waiter.DEFAULT_TIMEOUT_IN_SECONDS);
		clickElementById(id);
	}
	
}
