package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FrontendTest {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("https://acb0e094.ngrok.io/TestingAssignmentW9_war_exploded/reset");
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void addBankTest() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/");
        driver.findElement(By.name("name")).sendKeys("test bank");
        driver.findElement(By.cssSelector("input:nth-child(4)")).sendKeys("bitest");
        driver.findElement(By.cssSelector("input:nth-child(5)")).click();

        Thread.sleep(3000);
        List<WebElement> bankList = driver.findElements(By.className("item-container"));

        // gets last element in list
        WebElement listItemContainer = bankList.get(bankList.size()-1);
        List<WebElement> spans = listItemContainer.findElements(By.tagName("span"));

        String actualName = spans.get(0).getText();
        String actualCVR = spans.get(1).getText();

        Assert.assertEquals("Name test bank", actualName);
        Assert.assertEquals("CVR bitest", actualCVR);
    }

    @Test
    public void addExistingBank() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/");
        driver.findElement(By.name("name")).sendKeys("test bank");
        driver.findElement(By.cssSelector("input:nth-child(4)")).sendKeys("bid123");
        driver.findElement(By.cssSelector("input:nth-child(5)")).click();
        Thread.sleep(3000);
        assertThat(driver.switchTo().alert().getText(), is("cvr exists"));
    }

    @Test
    public void getCustomer() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/");
        driver.findElement(By.linkText("customers")).click();
        driver.findElement(By.name("cpr")).sendKeys("id123");
        driver.findElement(By.cssSelector("input:nth-child(3)")).click();
        Thread.sleep(3000);

        List<WebElement> bankList = driver.findElements(By.className("item-container"));

        // gets last element in list
        WebElement listItemContainer = bankList.get(bankList.size()-1);
        List<WebElement> spans = listItemContainer.findElements(By.tagName("span"));

        String actualName = spans.get(0).getText();
        String actualCPR = spans.get(1).getText();

        Assert.assertEquals("Name Jacob", actualName);
        Assert.assertEquals("CPR id123", actualCPR);
    }

    @Test
    public void getNonExistingCustomer() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/");
        driver.findElement(By.linkText("customers")).click();
        driver.findElement(By.name("cpr")).sendKeys("noncus");
        driver.findElement(By.cssSelector("input:nth-child(3)")).click();
        Thread.sleep(3000);
        assertThat(driver.switchTo().alert().getText(), is("some error"));
    }

    @Test
    public void addMovement() throws InterruptedException {
        Boolean success = false;

        driver.get("http://localhost:5500/");
        driver.findElement(By.linkText("movements")).click();
        //driver.findElement(By.name("amount")).click();
        driver.findElement(By.name("amount")).sendKeys("420");
        driver.findElement(By.name("source")).sendKeys("42069");
        driver.findElement(By.name("target")).sendKeys("src123");
        driver.findElement(By.cssSelector("input:nth-child(7)")).click();

        Thread.sleep(4000);  // Let the user actually see something!

        List<WebElement> itemz = driver.findElements(By.cssSelector(".item"));

        for(WebElement item: itemz){
            List<WebElement> spans = item.findElements(By.cssSelector(("span")));

            if(spans.get(0).getText().equals("Source 42069") &&
                    spans.get(1).getText().equals("Target src123") &&
                    spans.get(3).getText().equals("Amount 420")){
                success = true;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void addInvalidMovement() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/");
        driver.findElement(By.linkText("movements")).click();
        driver.findElement(By.cssSelector(".action-container")).click();

        driver.findElement(By.name("amount")).sendKeys("420");
        driver.findElement(By.name("source")).sendKeys("invalid");
        driver.findElement(By.name("target")).sendKeys("invalid2");
        driver.findElement(By.cssSelector("input:nth-child(7)")).click();
        Thread.sleep(3000);

        assertThat(driver.switchTo().alert().getText(), is("some error"));
    }

}
