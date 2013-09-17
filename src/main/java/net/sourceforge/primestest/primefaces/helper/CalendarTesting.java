package net.sourceforge.primestest.primefaces.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CalendarTesting {
	
	public static final String DATE_FORMAT = "d/M/yyyy";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	
	public WebElement getCell(final WebElement calendar) {
		return calendar.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1).findElement(By.tagName("td"));
	}
	
	public Date getClickedDate(final WebElement element) {
		final String jsCall = element.getAttribute("onclick");
		final StringTokenizer jsCallTokenizer = new StringTokenizer(jsCall, ",");
		
		jsCallTokenizer.nextToken();
		final String monthString = jsCallTokenizer.nextToken();
		final String yearString = jsCallTokenizer.nextToken();
		final String dayString = element.findElement(By.tagName("a")).getText();
		final String dateAsString = toFormattedDateString(dayString, monthString, yearString);
		
		try {
			return dateFormat.parse(dateAsString);
		} catch (final ParseException e) {
			throw new RuntimeException("date parsing failed " + dateAsString);
		}
	}
	
	public Date getDateOnCell(final WebElement element) {
		return getClickedDate(getCell(element));
	}
	
	public String toFormattedDateString(final String dayString, final String monthString, final String yearString) {
		return dayString + "/" + (Integer.valueOf(monthString) + 1) + "/" + yearString;
	}
	
	public Date parseDateString(final String dateAsString) {
		try {
			return dateFormat.parse(dateAsString);
		} catch (final ParseException e) {
			throw new RuntimeException("date parsing failed " + dateAsString);
		}
	}
	
	public Calendar toCalendar(final Date date) {
		final Calendar selected = Calendar.getInstance();
		selected.setTime(date);
		return selected;
	}
}
