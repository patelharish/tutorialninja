package com.tutorialsninja.qa;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = new ChromeDriver();
		driver.get("https://tutorialsninja.com/demo/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
		driver.findElement(By.xpath("//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[normalize-space()='Login']")).click();

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void loginWithValidcredentials() {
		driver.findElement(By.id("input-email")).sendKeys("hkp@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		String actualresult = driver.findElement(By.linkText("Edit your account information")).getText();

		Assert.assertTrue(actualresult.contains("Edit your account information"), "you are not logged in");
	}

	@Test
	public void loginWithInvalidCredentials() {
		driver.findElement(By.id("input-email")).sendKeys("rrhkp@gmail.com"); // invalid email id
		driver.findElement(By.id("input-password")).sendKeys("123456"); // invalid password
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		String actualWarnings = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']"))
				.getText();
		Assert.assertTrue(actualWarnings.contains("Warning: No match for E-Mail Address and/or Password."),
				"Warning message is not displayed");
		System.out.println(actualWarnings);
	}

	@Test
	public void loginWithBlankCredentials() {
		driver.findElement(By.id("input-email")).sendKeys("");
		driver.findElement(By.id("input-password")).sendKeys("");
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		String actualWarnings = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']"))
				.getText();
		Assert.assertTrue(actualWarnings.contains("Warning: No match for E-Mail Address and/or Password."),
				"Warning message is not displayed");
		System.out.println(actualWarnings);
	}

	@Test
	public void verifyForgotPassword() {
		driver.findElement(By.linkText("Forgotten Password")).click();

		String actualMessage = driver.findElement(By.cssSelector("div[id='content'] p")).getText();
		String expectedMessage = "Enter the e-mail address associated with your account. Click submit to have a password reset link e-mailed to you.";

		Assert.assertEquals(actualMessage, expectedMessage,"forgot page not displayed");
		System.out.println(actualMessage);
		
		driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys("hkp@gmail.com"); // Enter a valid email id
		driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();
		
		String actualConfirmationMsg = driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']")).getText();
		Assert.assertTrue(actualConfirmationMsg.contains("An email with a confirmation link has been sent your email address."),"Confirmation message should not sent");
	}

}
