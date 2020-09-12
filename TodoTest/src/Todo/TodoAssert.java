package Todo;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

	public class TodoAssert {

	WebDriver driver;
	Actions act;
	WebElement element;
	
	@BeforeMethod
	public void openBrowser()
	{
		//Invoking URL through chromedriver
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe"); 
		driver = new ChromeDriver();
		driver.get("http://todomvc.com/examples/angularjs/#/"); 					
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		act = new Actions(driver);
	}
	
	@Test
	public void ToDoActions()
	{
		//Assessment 1:		To Add To Do List -> "Drink water every hour"
		//<--------------------------------------------------------------->
		
		//Adding a new To Do item -> "Drink water every hour" to the list
		driver.findElement(By.xpath("//input[@placeholder='What needs to be done?']")).sendKeys("Drink water every hour");
		act.sendKeys(Keys.ENTER).perform();
		
		//Asserting added To Do List got added successfully
		Assert.assertEquals(driver.findElement(By.xpath("//label[contains(@ng-dblclick,'editTodo(todo)')]")).getText(), "Drink water every hour");
		System.out.println("<-------------------Assessment 1----------------------------------->");
		System.out.println("Item : 'Drink water every hour' is added to list successfully");		
		
		//<---------------------------------------------------------------------------------------------------------------------------------------------->
		
		//Assessment 2:		To complete To Do List -> "Drink water every hour"		
		//<--------------------------------------------------------------------->
		
		//Marking a item as complete
		driver.findElement(By.xpath("//input[@ng-model='todo.completed']")).click();
		
		//Checking whether list got checked out
		Assert.assertEquals(driver.findElement(By.xpath("//label[@ng-dblclick='editTodo(todo)' and text()='Drink water every hour']")).getCssValue("text-decoration-line"), "line-through");
		System.out.println("<-------------------Assessment 2----------------------------------->");
		System.out.println("Verification of crossed out funcitonality of Item: 'Drink water every hour' is successful");
		
		//Checking counter for active To Do list
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='todo-count']/strong")).getText(),"0");
		System.out.println("Counter on the bottom left is verified successfully");
		
				
		//<---------------------------------------------------------------------------------------------------------------------------------------------->
		 
		 //Assessment 3:	To delete a To Do List -> "Drink water every hour"	
		//<--------------------------------------------------------------------->
		
		//Deleting a To Do item
		act.moveToElement(driver.findElement(By.xpath("//button[@class='destroy']"))).click().build().perform();
		 		 
		try {
					//Verifying whether deleted To Do list is present
		       		element = driver.findElement(By.xpath("//ul[@class='todo-list']/li/div/label"));
		       		System.out.println("<-------------------Assessment 3----------------------------------->");
		       		Assert.assertTrue(false, "Item : 'Drink water every hour' is not deleted");
		       		
		    }catch(Exception e) {
		    		//If Deleted To Do List not present 
		    		System.out.println("<-------------------Assessment 3----------------------------------->");
		    	 	System.out.println("Item : 'Drink water every hour' is removed from the list successfully");
		    }
		 
		//<---------------------------------------------------------------------------------------------------------------------------------------------->
		
		 //Assessment 4:	Filter verification for Active and Completed buttons
		//<--------------------------------------------------------------------->
		 
		 //Adding list of To Do items
		 driver.findElement(By.xpath("//input[@placeholder='What needs to be done?']")).sendKeys("Drink water every hour");
		 act.sendKeys(Keys.ENTER).perform();
		 driver.findElement(By.xpath("//input[@placeholder='What needs to be done?']")).sendKeys("Exercise is good for health");
		 act.sendKeys(Keys.ENTER).perform();
		 driver.findElement(By.xpath("//input[@placeholder='What needs to be done?']")).sendKeys("Sleep is necessary");
		 act.sendKeys(Keys.ENTER).perform();
		 
		 //To complete To Do List: "Exercise is good for health"
		 try
		 {
			 if(driver.findElement(By.xpath("//label[@ng-dblclick='editTodo(todo)' and text()='Exercise is good for health']/preceding-sibling::input")).isEnabled())
			 {
				 driver.findElement(By.xpath("//label[@ng-dblclick='editTodo(todo)' and text()='Exercise is good for health']/preceding-sibling::input")).click();
			 }
		 }catch(Exception e) {}
		 
		 //Filter verification for Active button
		 //<~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~>
		 
		 //Clicking Active button
		 driver.findElement(By.xpath("//a[text()='Active']")).click();	
		 
		//Verifying whether Active items alone are present by checking whether item is crossed out or not
		 
		 element = driver.findElement(By.xpath("//ul[@class='todo-list']"));
		 List<WebElement> items = element.findElements(By.tagName("Label"));
		
		 for(WebElement item: items)
		 {
			 if(item.getCssValue("text-decoration-line").equals("line-through"))
			 {
				 Assert.assertTrue(false,"Active Button filter is not working properly");
			 }
		 }
		 
		 System.out.println("<-------------------Assessment 4 : Active Filter----------------------------------->");
		 System.out.println("Active Button filter is working properly");
		 			 
	     	    
		 
		//Filter verification for Completed button
		//<~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~>
		 
		//Clicking Complete button
		 driver.findElement(By.xpath("//a[text()='Completed']")).click();
		
			 
		 //Verifying whether Completed items alone are present by checking whether item is crossed out or not
		 
		 element = driver.findElement(By.xpath("//ul[@class='todo-list']"));
		 List<WebElement> items1 = element.findElements(By.tagName("Label"));
		 for(WebElement item: items1)
		 {
			 if(!(item.getCssValue("text-decoration-line").equals("line-through")))
			 {
				 Assert.assertTrue(false,"Completed Button filter is not working properly");
			 }
		 }
		 
		 System.out.println("<-------------------Assessment 4 : Completed Filter----------------------------------->");
		 System.out.println("Completed Button filter is working properly");
	}	
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.quit();
	}	
}
