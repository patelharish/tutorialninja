package com.tutorialsninja.qa;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ResisterTest {

	WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = new ChromeDriver();
		driver.get("https://tutorialsninja.com/demo/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
		driver.findElement(By.xpath("//a[@title='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		System.out.println(driver.getTitle());
		
}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void resisterWithMandatoryField() {
		driver.findElement(By.id("input-firstname")).sendKeys("ram");
		driver.findElement(By.id("input-lastname")).sendKeys("patel");
		driver.findElement(By.id("input-email")).sendKeys("rkpp@gmail.com");
		driver.findElement(By.id("input-telephone")).sendKeys("9977345212");
		driver.findElement(By.id("input-password")).sendKeys("1234");
		driver.findElement(By.id("input-confirm")).sendKeys("1234");
		driver.findElement(By.xpath("//input[@type='checkbox']")).click();
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		String actualAccountCreatedMsg = driver.findElement(By.xpath("//p[contains(text(),'Congratulations! Your new account has been success')]")).getText();
		
		Assert.assertTrue(actualAccountCreatedMsg.contains("Congratulations! Your new account has been successfully created!"),"your account is not created");
		
		
	}
}
