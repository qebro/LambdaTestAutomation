import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

public class SampleTest extends LambdaTestSetup {

    @Test( timeOut = 20000)
    public void testSimple() {
        try {
            driver.get("https://www.lambdatest.com");
            String currentHandle= driver.getWindowHandle();
            WebDriverWait wait=new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__next\"]/div[1]/section[1]/div/div/div[1]/h1")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/section[8]/div/div/div/div/a")));
            driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/section[8]/div/div/div/div/a")).sendKeys(Keys.chord(Keys.CONTROL,Keys.ENTER));
            Set<String> allWindowHandles = driver.getWindowHandles();
            for(String handle : allWindowHandles) {
                System.out.println("Window handle : " + handle);
                if(!handle.equalsIgnoreCase(currentHandle)){
                    driver.switchTo().window(handle);
                }
            }
            Assert.assertEquals("https://www.lambdatest.com/integrations", driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}