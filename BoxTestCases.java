package com.box.basic.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import unitee.annotations.AfterClassInstance;
import unitee.annotations.AfterTest;
import unitee.annotations.BeforeClassInstance;
import unitee.annotations.BeforeTest;
import unitee.annotations.Instances;
import unitee.annotations.Skip;
import unitee.annotations.TestClass;
import unitee.annotations.TestMethod;
import unitee.assertions.Assertions;

@Instances(3)
@TestClass
public class BoxTestCases extends BoxUtilities {
	
	@BeforeClassInstance
	public void setUpInstance() throws Exception {
		launchBrowserWithBoxDrive();
	}
	
	@BeforeTest
	public void setUpMethod(){
		validLogin("hunterwow601@gmail.com" , "XXXX");
	}
	
	@TestMethod
	public void createFolder() throws Exception{
		deleteFolderIfExists(firstFolderName);
		createFolder(firstFolderName);
		assertFolderExist(firstFolderName);
	}
	
	@TestMethod
	public void deleteFolderFromMenuBar() throws Exception{
		createFolderIfNotExists(firstFolderName);
		deleteFolderFromMenu(firstFolderName);
		assertFolderDoesNotExist(firstFolderName);
	}
	
	@TestMethod
	public void moveFolderFromMenuBar() throws Exception{
		prerequisiteForMoveOrCopy();
		moveFolderFromMenu(firstFolderName , seconfFolderName);
		assertFolderMove();
	}
	
	@TestMethod
	public void copyFolderFromMenuBar() throws Exception {
		prerequisiteForMoveOrCopy();
		copyFolderFromMenu(firstFolderName , seconfFolderName);
		assertFolderCopy();
	}
	
	@TestMethod
	public void renameFolderFromMenuBar() throws Exception{
		prerequisiteForRenameFolder();
		clickOnRenameFromMenu(firstFolderName);
		renameAction(seconfFolderName);
		assertRenameFolder(seconfFolderName);
	}

}
