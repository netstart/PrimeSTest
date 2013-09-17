package net.sourceforge.primestest.selenium.helper;

import java.util.concurrent.TimeUnit;

import net.sourceforge.primestest.selenium.ETimeUnitInSeconds;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class Waiter {
	private final Logger LOGGER = Logger.getLogger(getClass());
	
	private static final String JQUERY_ACTIVE_CONNECTIONS_QUERY = "return $.active == 0;";
	
	public static final int DEFAULT_SLEEP_TIME_IN_SECONDS = 1;
	
	public static final int DEFAULT_ANIMATED_INTERVAL_IN_SECONDS = 400;
	
	public static final int DEFAULT_TIMEOUT_IN_SECONDS = 60;
	
	private final WebDriver driver;
	
	public Waiter(final WebDriver driver) {
		new WebDriverWait(driver, ETimeUnitInSeconds.CINCO_SEGUNDOS.getTime());
		this.driver = driver;
	}
	
	/**
	 * Este método deve ser evitado
	 */
	public WebElement getElementImplThreadSleep(final String id, final int waitSecond) {
		final By by = By.id(id);
		WebElement item = null;
		if (driver != null && by != null) {
			try {
				Thread.sleep(waitSecond * 1000L);
				item = driver.findElement(by);
			} catch (final Exception e) {
				return null;
			}
		}
		
		return item;
	}
	
	public WebElement getElementExistImplPollingById(final String id) {
		final By by = By.id(id);
		if (waitUntilElementExists(by)) {
			return driver.findElement(by);
		}
		
		throw new NoSuchElementException(String.format("Elemento não encontrado, timeout em segundos: %s", DEFAULT_ANIMATED_INTERVAL_IN_SECONDS));
	}
	
	public Boolean waitUntilElementExistsById(final String id) {
		return waitUntilElementExists(By.id(id));
	}
	
	public Boolean waitUntilElementExists(final By by) {
		return waitUntilElementExists(by, DEFAULT_TIMEOUT_IN_SECONDS);
	}
	
	public Boolean waitUntilElementExistsById(final String id, final int timeout) {
		return waitUntilElementExists(By.id(id), timeout);
	}
	
	public Boolean waitUntilElementExists(final By by, final int timeOut) {
		return waitForConditionImplPolling(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(final WebDriver wd) {
				wd.findElement(by);
				return true;
			}
		}, timeOut);
	}
	
	public void waitIsDisplayed(final String id, final int timeOut) {
		waitForConditionImplPolling(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(final WebDriver wd) {
				final WebElement element = wd.findElement(By.id(id));
				return element.isDisplayed();
			}
		}, timeOut);
		
	}
	
	public void waitIsEnabled(final String id, final int timeOut) {
		waitForConditionImplPolling(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(final WebDriver wd) {
				final WebElement element = wd.findElement(By.id(id));
				return element.isEnabled();
			}
		}, timeOut);
	}
	
	public Boolean waitForConditionImplPolling(final ExpectedCondition<Boolean> condition, final int timeOut) {
		return new FluentWait<WebDriver>(driver).withTimeout(timeOut, TimeUnit.SECONDS).pollingEvery(DEFAULT_SLEEP_TIME_IN_SECONDS, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).until(condition);
	}
	
	public WebElement waitForCondition(final ExpectedCondition<WebElement> condition, final int timeOut) {
		return new FluentWait<WebDriver>(driver).withTimeout(timeOut, TimeUnit.SECONDS).pollingEvery(DEFAULT_SLEEP_TIME_IN_SECONDS, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).until(condition);
	}
	
	/**
	 * Exemplo de function
	 * 
	 * <PRE>
	 * 	new Function<WebDriver, WebElement>() {
	 *  	public WebElement apply(WebDriver driver) {
	 * 			return driver.findElement(locator);
	 * 		}
	 * 	});
	 * </PRE>
	 */
	public WebElement waitForFunctionImplPolling(final Function<WebDriver, WebElement> function, final int timeOut) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.withTimeout(timeOut, TimeUnit.SECONDS);
		wait.pollingEvery(DEFAULT_SLEEP_TIME_IN_SECONDS, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		
		return wait.until(function);
	};
	
	/**
	 * Deve ser evitado, por tornar internitente o cenário
	 */
	public void waitForOneSecond() {
		waitForSeconds(1);
	}
	
	/**
	 * Deve ser evitado, por tornar internitente o cenário
	 */
	public void waitForSeconds(final int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (final InterruptedException e) {
		}
	}
	
	public void waitAjaxJQueryRequestCompleteImplPolling() {
		new FluentWait<WebDriver>(driver).withTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(DEFAULT_SLEEP_TIME_IN_SECONDS, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(final WebDriver d) {
						final JavascriptExecutor jsExec = (JavascriptExecutor) d;
						return (Boolean) jsExec.executeScript(JQUERY_ACTIVE_CONNECTIONS_QUERY);
					}
				});
	}
	
	public void waitAjaxJQueryComplete() {
		final WebDriverWait timeWait = timeWait(ETimeUnitInSeconds.UM_MINUTO);
		waitAjaxJQueryComplete(timeWait);
	}
	
	public void waitAjaxJQueryComplete(final WebDriverWait wait) {
		wait.until(jQueryComplete());
	}
	
	/**
	 * Especifico para JQuery
	 */
	public static ExpectedCondition<Boolean> jQueryComplete() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(final WebDriver d) {
				return (Boolean) ((JavascriptExecutor) d).executeScript("return window.jQuery.active == 0");
			}
		};
	}
	
	public WebDriverWait timeWait(final ETimeUnitInSeconds eTimeUnitInSeconds) {
		return timeWait(eTimeUnitInSeconds.getTime());
		
	}
	
	public WebDriverWait timeWait(final long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		LOGGER.info(String.format("TimeWait configurado para %s segundos", time));
		return new WebDriverWait(driver, time);
	}
	
	/**
	 * Waits until body elements animated with JS.
	 */
	public void waitUntilAllAnimationsComplete() {
		waitUntilAnimationCompletes("body *");
	}
	
	/**
	 * Waits until given selector elements animated with JS.
	 * 
	 * @param selector
	 *            : jQuery element selector
	 */
	public void waitUntilAnimationCompletes(final String selector) {
		new FluentWait<WebDriver>(driver).withTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(DEFAULT_ANIMATED_INTERVAL_IN_SECONDS, TimeUnit.MILLISECONDS).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(final WebDriver d) {
						return (Boolean) ((JavascriptExecutor) d).executeScript("return ! $('" + selector + "').is(':animated');");
					}
				});
	}
	
}
