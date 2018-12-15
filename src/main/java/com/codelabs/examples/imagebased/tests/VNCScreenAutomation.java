package com.codelabs.examples.imagebased.tests;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.codelabs.examples.imagebased.commands.SikuliCommandBase;

public class VNCScreenAutomation {

	SikuliCommandBase imageCommands;

	@BeforeMethod
	public void beforeMethod() throws Exception{
		imageCommands = new SikuliCommandBase();
	}
	
	
	@Test
	public void remoteDesktopAutomation() throws Exception {
		
		imageCommands.open("Microsoft Remote Desktop");
		
		imageCommands.click("server_name.png");
		imageCommands.click("start_btn.png");
		imageCommands.click("continue_btn.png");
		
		imageCommands.waitForImage("chrome_icon.png", 300000);
		imageCommands.click("chrome_icon.png");
		imageCommands.click("search_bar.png");
		imageCommands.type("search_bar.png", "Sikuli Automation\n");
		imageCommands.click("search_result.png");
		
		Thread.sleep(5000);
		imageCommands.click("close_btn.png");
		
	}
	
	
	
	
	
	

	@AfterMethod
	public void afterMethod() throws IOException {
		imageCommands.closeApp();
	}

}
