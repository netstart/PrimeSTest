package net.sourceforge.primestest.primefaces.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sourceforge.primestest.selenium.helper.Finder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * http://code.google.com/p/primefaces/source/browse/examples/trunk/showcase/src/test/java/com/prime/showcase/integration/datatable
 * 
 */
public class DataTableHelper {
	
	private final Finder finder;
	
	/**
	 * Uma linha criada por um
	 * <p:dataTable>
	 * comumente é formatada da seguinte forma:
	 * 
	 * <tr class="ui-widget-content ui-datatable-even" data-rk="421" data-ri="0">
	 * <tr class="ui-widget-content ui-datatable-odd" data-rk="422" data-ri="1">
	 * 
	 * Isto quer dizer que podemos utilizar o .ui-widget-content para pegar as linhas
	 */
	public static final String ROW_CLASS = ".ui-widget-content";
	
	public static final String COLUMN_CLASS = ".ui-dt-c";
	
	public enum Columns {
		MODEL,
		YEAR,
		MANUFACTURER,
		COLOR,
	}
	
	public DataTableHelper(final Finder finder) {
		this.finder = finder;
	}
	
	public List<String> getTableColumns(final List<WebElement> rows, final int column) {
		final List<String> columns = new ArrayList<String>();
		String eachItem;
		WebElement eachElement;
		for (final WebElement eachRow : rows) {
			eachElement = eachRow.findElements(By.cssSelector(COLUMN_CLASS)).get(column);
			eachItem = eachElement.getText();
			columns.add(eachItem);
		}
		return columns;
	}
	
	/**
	 * @param idDataTableData
	 *            subviewBcEdit:tabelaBcs_data
	 */
	public int getTableNumRows(final String idDataTableData) {
		final List<WebElement> rows = getTableRows(idDataTableData);
		return rows.size();
	}
	
	/**
	 * <tbody id="subviewBcEdit:tabelaBcs_data" class="ui-datatable-data ui-widget-content">
	 * 
	 * @param idDataTableData
	 *            subviewBcEdit:tabelaBcs_data
	 */
	public List<WebElement> getTableRows(final String idDataTableData) {
		final WebElement dataTableElement = finder.findElementById(idDataTableData);
		final List<WebElement> rows = dataTableElement.findElements(By.cssSelector(ROW_CLASS));
		return rows;
	}
	
	public String getItemByColumnAndRow(final List<WebElement> rows, final int rowIndex, final int columnIndex) {
		return rows.get(rowIndex).findElements(By.cssSelector(COLUMN_CLASS)).get(columnIndex).getText();
	}
	
	public List<String> getRowByRowIndex(final List<WebElement> rows, final int rowIndex, final int firstColumn, final int endColumn) {
		final List<String> items = new ArrayList<String>();
		String eachItem;
		for (int columnIndex = firstColumn; columnIndex < endColumn; columnIndex++) {
			eachItem = getItemByColumnAndRow(rows, rowIndex, columnIndex);
			items.add(eachItem);
		}
		return items;
	}
	
	public List<String> getModels(final List<WebElement> rows) {
		return getTableColumns(rows, Columns.MODEL.ordinal());
	}
	
	public List<String> getYears(final List<WebElement> rows) {
		return getTableColumns(rows, Columns.YEAR.ordinal());
	}
	
	public List<String> getManufacturers(final List<WebElement> rows) {
		return getTableColumns(rows, Columns.MANUFACTURER.ordinal());
	}
	
	public List<String> getColors(final List<WebElement> rows) {
		return getTableColumns(rows, Columns.COLOR.ordinal());
	}
	
	public int getAnotherRandomNumber(final int currentNumber, final int max) {
		final int randomNumber = new Random().nextInt(max);
		if (randomNumber == currentNumber) {
			getAnotherRandomNumber(currentNumber, max);
		}
		return randomNumber;
	}
	
}
