Prime faces
Ajax request
Selenium
JUnit


Example:

	public APageObject(final WebDriver driver) {
		this.driver = driver;
		
		finder = new Finder(driver);
		waiter = new Waiter(driver);
		
		action = new Actioner(driver, finder);
		
		//to wait five seconds
		waiter.timeWait(ETimeUnitInSeconds.CINCO_SEGUNDOS);
		
		//to click on link
		action.clickElementByLinkText("Resultado da Importação");
		
		//to click on element by id
		action.clickElementById("btnOKModalConfirmActions");
		
		// wait be enabled, timeout 10 seconds
		waiter.waitIsEnabled("accordionBackbone:idUpload_input", 10);
		
		// find element by xpah and click
		finder.findElementByXpath("//table[@id='accordionBackbone:options']/tbody/tr/td/div/div[2]").click();
		
		// wait ajax request complete
		waiter.waitAjaxJQueryComplete();
		
		//wait for css selector
		finder.findElementByCssSelector("#menuItemCadastrarBC > span.ui-menuitem-text").click();
		
		//wait for one second
		waiter.waitForOneSecond();
		
		//
		action.mouseHoverByLinkText("BC");
		
		action.clickWhenIsDisplayed("btnOK");
		
		waiter.waitUntilElementExistsById("ap_cad_basico:cmb_regional");
	}