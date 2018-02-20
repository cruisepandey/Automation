package com.box.basic.testcases;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import unitee.assertions.Assertions;

public class BoxUtilities{
	
	protected WebDriver chromeWebApp;
	protected WebDriverWait wait;
	protected Select dropdown;
	protected JavascriptExecutor jse;
	protected Actions action ;
	protected String firstFolderName = "Abhishek";
	protected String seconfFolderName = "Pandey";
	protected String rootFolder = "root";
	
	public void launchBrowserWithBoxDrive(){
		System.setProperty("webdriver.chrome.driver", "F:/Automation_Learning/Automation_workspace/BoxUiAutomation/autocognite-arjuna/bin/tools/uidrivers/chromedriver.exe");
		chromeWebApp = new ChromeDriver(); 
		chromeWebApp.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(chromeWebApp,10);
		chromeWebApp.manage().window().maximize();
		chromeWebApp.get("https://account.box.com/login");
	}
	
	public void validLogin(String userName ,String pswd){
		WebElement emailField = chromeWebApp.findElement(By.name("login"));
		emailField.click();
		emailField.clear();
		emailField.sendKeys(userName);
		WebElement password = chromeWebApp.findElement(By.name("password"));
		password.click();
		password.sendKeys(pswd);
		chromeWebApp.findElement(By.cssSelector("div[class='form-buttons']>button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class*=popout-toggle-button]")));
	}
	
	public void logout(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*=main-header-profile]")));
		chromeWebApp.findElement(By.cssSelector("div[class*=main-header-profile]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-resin-target=accountmenu]+ul")));
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log Out")));
		chromeWebApp.findElement(By.linkText("Log Out")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login-container")));
	}
	
	public void deleteFolderFromMenu(String folderName){
		selectFolder(folderName);
		clickOnDeleteButtonFromMenu();
		deleteAction(folderName);
	}
	
	public void deleteFolderFromDropDownMoreAction(String folderName){
		selectFolder(folderName);
		rightClickOnSelectedFolder(folderName);
		hoverOnMoreActions();
		clickOnDeleteFromMoreAction();
		deleteAction(folderName);
	}
	
	public void deleteFolderFromDotMenuMoreAction(String folderName){
		selectFolder(folderName);
		clickOnDotMenu();
		hoverOnMoreActions();
		clickOnDeleteFromMoreAction();
		deleteAction(folderName);
	}
	
	public void moveFolderFromMenu(String folderName , String seconfFolderName ) throws InterruptedException{
		selectFolder(folderName);
		clickOnMoveOrCopyButtonFromMenu();
		moveAction();
	}
	
	public void moveFolderFromDropDown(String folderName , String seconfFolderName ) throws InterruptedException{
		selectFolder(folderName);
		rightClickOnSelectedFolder(folderName);
		clickOnMoveOrCopyButton();
		moveAction();
	}
	
	public void moveFolderFromDotMenu(String folderName , String seconfFolderName){
		selectFolder(folderName);
		clickOnDotMenu();
		clickOnMoveOrCopyButton();
		moveAction();
	}
	
	public void copyFolderFromMenu(String folderName , String seconfFolderName ) throws InterruptedException{
		selectFolder(folderName);
		clickOnMoveOrCopyButtonFromMenu();
		copyAction();
	}
	
	public void copyFolderFromDropDown(String folderName , String seconfFolderName ) throws InterruptedException{
		selectFolder(folderName);
		rightClickOnSelectedFolder(folderName);
		clickOnMoveOrCopyButton();
		copyAction();
	}
	
	public void copyFolderFromDotmenu(String folderName , String seconfFolderName ) throws InterruptedException{
		selectFolder(folderName);
		clickOnDotMenu();
		clickOnMoveOrCopyButton();
		copyAction();
	}
	
	public void prerequisiteForMoveOrCopy() throws Exception{
		deleteFolderIfExists(seconfFolderName);
		deleteFolderIfExists(rootFolder); 
		createFolder(rootFolder);
		clickOnFolder(rootFolder);
		waitForNoFolderIcon();
		createFolder(seconfFolderName);
		createFolder(firstFolderName);
	}
	
	public void prerequisiteForRenameFolder() throws Exception{
		deleteFolderIfExists(seconfFolderName);
		deleteFolderIfExists(firstFolderName);
		createFolder(firstFolderName);
		selectFolder(firstFolderName);
	}
	
	public void clickOnRenameFromDropDown(String folderName){
		rightClickOnSelectedFolder(folderName);
		hoverOnMoreActions();
		clickOnRenameFromMoreAction();
	}
	
	public void clickOnRenameFromDotMenu(){
		clickOnDotMenu();
		hoverOnMoreActions();
		clickOnRenameFromMoreAction();
	}
	
	public boolean iSFolderExists(String firstFolderName) throws InterruptedException{
		Thread.sleep(3000);
		List<WebElement> allFolderssName = chromeWebApp.findElements(By.cssSelector("ol[class*=list-view] a[class=item-name-link]"));
		for(int i =0 ; i<allFolderssName.size() ; i++){						
				WebElement folderNames = allFolderssName.get(i);
				String a = folderNames.getText();
				String alowerCase = a.toLowerCase();
				String aupperCase = a.toUpperCase();
				if(a.equals(firstFolderName) || alowerCase.equals(firstFolderName) || aupperCase.equals(firstFolderName)){
					return true;
				}
		}
		return false;
	}
	
	public void deleteFolderIfExists(String folderName) throws InterruptedException{
		if(iSFolderExists(folderName)){
			deleteFolderFromMenu(folderName);
		}
	}
	
	public void createFolderIfNotExists(String folderName) throws InterruptedException{
		if(!iSFolderExists(folderName)){
			createFolder(folderName);
		}
	}
	
	public void assertFolderExist(String folderName) throws Exception{
		Assertions.assertTrue("", iSFolderExists(folderName));
	}
	
	public void assertFolderDoesNotExist(String folderName) throws Exception {
		Assertions.assertFalse("", iSFolderExists(folderName));
	}
	
	public void assertFolderMove() throws Exception{
		WebElement notification = chromeWebApp.findElement(By.cssSelector("span[id*=notification]"));
		Assertions.assertEquals("", notification.getText(), "Your folder was successfully moved to "+seconfFolderName+".");
		Assertions.assertFalse("", iSFolderExists(firstFolderName));
		clickOnFolder(seconfFolderName);
		Assertions.assertTrue("", iSFolderExists(firstFolderName));
	}

	public void assertFolderCopy() throws Exception{
		WebElement notification = chromeWebApp.findElement(By.cssSelector("span[id*=notification]"));
		Assertions.assertEquals("", notification.getText(), "Your folder was successfully copied to "+seconfFolderName+".");
		Assertions.assertTrue("", iSFolderExists(firstFolderName));
		clickOnFolder(seconfFolderName);
		Assertions.assertTrue("", iSFolderExists(firstFolderName));
	}
	
	public void assertRenameFolder(String seconfFolderName) throws Exception{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[id*=notification]")));
		WebElement notification = chromeWebApp.findElement(By.cssSelector("span[id*=notification]"));
		Assertions.assertEquals("", notification.getText(), "The folder has been renamed to \""+seconfFolderName+"\".");
	}
	
	public void selectFolder(String folderName){
		chromeWebApp.findElement(By.xpath(String.format("//a[text()=' %s']/ancestor::li", folderName))).click();
	}
	
	public void clickOnFolder(String folderName){
		chromeWebApp.findElement(By.xpath(String.format("//a[text()=' %s']" , folderName))).click();
		chromeWebApp.findElements(By.cssSelector("ol[class*=list-view] a[class=item-name-link]"));
	}
	
	public void waitForNoFolderIcon(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("svg[class*='files-empty-state-svg']")));
	}

	public void clickOnRenameFromMenu(String folderName){
		mouseHover(chromeWebApp.findElement(By.xpath(String.format("//a[text()=' %s']" , folderName))));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("//a[text()=' %s']/following-sibling::button" , folderName))));
		chromeWebApp.findElement(By.xpath(String.format("//a[text()=' %s']/following-sibling::button" , folderName))).click();;
	}
	
	public void clickOnDeleteButtonFromMenu(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class*='delete-btn']")));
		chromeWebApp.findElement(By.cssSelector("button[class*='delete-btn']")).click();
	}
	
	public void clickOnMoveOrCopyButtonFromMenu(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class*='movecopy-btn']")));
		chromeWebApp.findElement(By.cssSelector("button[class*='movecopy-btn']")).click();
	}
	
	public void clickOnDownloadButtonFromMenu(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class*='download-btn']")));
		chromeWebApp.findElement(By.cssSelector("button[class*='download-btn']")).click();
	}
	
	public void clickOnSendItemButtonFromMenu(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class*='share-via-email-btn']")));
		chromeWebApp.findElement(By.cssSelector("button[class*='share-via-email-btn']")).click();
	}
	
	public void clickOnDeleteFromMoreAction(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[id*=moreactions-submenu-item]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul[id*=moreactions-submenu-item] button[data-menu-event=delete]")));
		chromeWebApp.findElement(By.cssSelector("ul[id*=moreactions-submenu-item] button[data-menu-event=delete]")).click();
	}
	
	public void clickOnRenameFromMoreAction(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[id*=moreactions-submenu-item]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul[id*=moreactions-submenu-item] button[data-menu-event=rename]")));
		chromeWebApp.findElement(By.cssSelector("ul[id*=moreactions-submenu-item] button[data-menu-event=rename]")).click();
	}
	
	public void deleteAction(String folderName){
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("popup")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class*=popup-confirm-btn]>span[class=btn-text]")));
		chromeWebApp.findElement(By.cssSelector("button[class*=popup-confirm-btn]>span[class=btn-text]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("span[id*=notification]"), "Item successfully moved to trash."));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(String.format("//a[text()=' %s']/ancestor::li", folderName))));
	}
	
	public void renameAction(String seconfFolderName){
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("popup")));
		chromeWebApp.findElement(By.className("popup-prompt-input")).clear();
		chromeWebApp.findElement(By.className("popup-prompt-input")).sendKeys(seconfFolderName);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-event-type=ok]")));
		chromeWebApp.findElement(By.cssSelector("button[data-event-type=ok]")).click();
	}
	
	public void moveAction(){
		selectFolderToCopyOrMove();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-type='move-btn']")));
		chromeWebApp.findElement(By.cssSelector("button[data-type='move-btn']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[id*=notification]")));
	}
	
	public void copyAction(){
		selectFolderToCopyOrMove();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-type='copy-btn']")));
		chromeWebApp.findElement(By.cssSelector("button[data-type='copy-btn']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[id*=notification]")));
	}
	
	public void rightClickOnSelectedFolder(String folderName){
		rightClick(chromeWebApp.findElement(By.xpath("//a[text()=' Abhishek']/ancestor::li")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[id*=menu-file-list-]")));  
	}
	
	public void clickOnMoveOrCopyButton(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class*=is-visible] button[data-resin-target=movecopy]")));
		chromeWebApp.findElement(By.cssSelector("div[class*=is-visible] button[data-resin-target=movecopy]")).click();
	}
	
	public void hoverOnMoreActions(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class*=is-visible] button[aria-owns*=moreactions-]")));
		mouseHover(chromeWebApp.findElement(By.cssSelector("div[class*=is-visible] button[aria-owns*=moreactions-]")));
	}
	
	public void clickOnDotMenu(){
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Abhishek']/ancestor::div[@class='file-list-name']/following-sibling::div[@class='file-list-item-actions']/button")));
		chromeWebApp.findElement(By.xpath("//a[text()=' Abhishek']/ancestor::div[@class='file-list-name']/following-sibling::div[@class='file-list-item-actions']/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[id*=menu-file-list-]")));  
	}
	
	public void selectFolderToCopyOrMove(){
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[aria-label*='Move or Copy']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='move-copy-popup'] ul[role='group']")));
		chromeWebApp.findElement(By.xpath(String.format("//div[@class='move-copy-popup']/descendant::span[text()='%s']",seconfFolderName))).click();
	}
	
	public void	folderInsideOptions(){
		wait.until(ExpectedConditions.elementToBeClickable(By.className("action-bar-breadcrumb-item-name")));
		chromeWebApp.findElement(By.className("action-bar-breadcrumb-item-name")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class*=breadcrumb-current]+ul")));
	}
	
	public void clickOnRenameFromInsideFolder(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul[class=menu] button[data-resin-target=rename]")));
		chromeWebApp.findElement(By.cssSelector("ul[class=menu] button[data-resin-target=rename]")).click();
	}
	
	public void clickOnDeleteFromInsideFolder(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul[class=menu] button[data-resin-target=trash]")));
		chromeWebApp.findElement(By.cssSelector("ul[class=menu] button[data-resin-target=trash]")).click();
	}
	
	public void clickOnMoveOrCopyFromInsideFolder(){
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul[class=menu] button[data-resin-target=movecopy]")));
		chromeWebApp.findElement(By.cssSelector("ul[class=menu] button[data-resin-target=movecopy]")).click();
	}
	
	public void createFolder(String folderName) throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a[class=btn]>span[class=text]")));
		chromeWebApp.findElement(By.cssSelector("a[class=btn]>span[class=text]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-newdropdown")));
		chromeWebApp.findElement(By.cssSelector("button[data-type='new-folder']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("popup-contents")));
		wait.until(ExpectedConditions.elementToBeClickable(By.name("folderName")));
		chromeWebApp.findElement(By.name("folderName")).sendKeys(folderName);
		chromeWebApp.findElement(By.name("folderName")).sendKeys(Keys.RETURN);
//		WebElement notification = chromeWebApp.findElement(By.cssSelector("span[id*=notification]"));
//		wait.until(ExpectedConditions.invisibilityOf(notification));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Folder was created successfully.']")));
		//wait.until(ExpectedConditions.visibilityOf(notification));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Folder was created successfully.']")));
	}
	
	public void scrollDown(){
		jse = (JavascriptExecutor)chromeWebApp;
		jse.executeScript("scroll(0, 250)");
	}
	
	public void mouseHover(WebElement element){
		action = new Actions(chromeWebApp);
		action.moveToElement(element).build().perform();
	}
	
	public void rightClick(WebElement element){
		action = new Actions(chromeWebApp);
		action.moveToElement(element);
		action.contextClick(element).build().perform(); 
	}
	
	public String randomNames(){
		String randomNames = UUID.randomUUID().toString().trim();
		return randomNames;
	}
	
	public void dropDownByValue(WebElement element , String value){
		dropdown = new Select(element);
		dropdown.selectByValue(value);
	}
	
	public void dropDownByVisibleText(WebElement element , String visibleText){
		dropdown = new Select(element);
		dropdown.selectByVisibleText(visibleText);;
	}
	
	public void browserClose(){
		chromeWebApp.close(); 
	}
}
