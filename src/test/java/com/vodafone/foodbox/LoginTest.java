package com.vodafone.foodbox;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {
	  WebDriver driver;
	  
	  @Test(priority=0)
	  public void testLogin() throws InterruptedException {
		 String username="user1@foodbox.com";
		 String password="password";
		 driver.findElement(By.xpath("//a[text()='UserLogin']")).click();
		 driver.findElement(By.id("exampleInputEmail1")).sendKeys(username);
		 driver.findElement(By.id("exampleInputPassword1")).sendKeys(password);
		 driver.findElement(By.xpath("//button[text()='Login']")).click();
		 Thread.sleep(2000);
		 String welcomeString=driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/h2")).getText();
		 System.out.println("WelcomeString: "+welcomeString);
		 Assert.assertEquals(welcomeString, "Welcome "+username);
	  }
	  
	  @Test(priority=1)
	  public void testPutFoodIntoCart() throws InterruptedException{
		  driver.findElement(By.partialLinkText("Order Food")).click();
		  Thread.sleep(1000);
		  driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[3]/div[3]/button")).click(); //Add to Cart of first food in list
		  driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[4]/div[3]/button")).click(); //Add to Cart of second food in list
		  driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/a/button")).click();
		  int countElements = driver.findElements(By.xpath("//div[@class='checkOutItem']")).size();
		  System.out.println("Elements in cart: "+countElements);
		  Assert.assertEquals(2, countElements);
		  
		  

	  }
	  
	  @Test(priority=2)
	  public void testOrderButton() throws InterruptedException {
		  //scroll down
		  JavascriptExecutor js = (JavascriptExecutor) driver;
		  js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		  WebElement web = driver.findElement(By.id("orderButton"));
		  System.out.println("Orderbutton: "+web.getText());
		  web.click();
		  Thread.sleep(1000);
		  //String text = driver.findElement(By.id("orderButton")).getText();
		  //Assert.assertEquals(text, "Food Ordered");

	  }
	  
	

	  @BeforeTest
	  public void beforeMethod() {
		  System.out.println("Set Chromedriver");
		  System.setProperty("webdriver.chrome.driver", "C:\\lokal\\webdriver\\chromedriver.exe");
		  driver = new ChromeDriver();
		  String url="http://localhost:3000/";
		  System.out.println("Url: "+url);
		  driver.manage().window().maximize();
		  driver.get(url);
	  }

	  @AfterTest
	  public void afterMethod() throws InterruptedException {
		  Thread.sleep(5000);
		  ////driver.close();
		  driver.quit();
	  }	
}
