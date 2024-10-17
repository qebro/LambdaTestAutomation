import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTestOne {
    private static WebDriver driver;
    public static String hubURL = "https://*******:*******@hub.lambdatest.com/wd/hub";

  public static void main(String[] args) throws MalformedURLException {
    initDriver();
    test();
    quitWebDriver();
  }

  private static void initDriver() throws MalformedURLException {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("platform", "Windows 10");
      capabilities.setCapability("browserName", "Chrome");
      capabilities.setCapability("browserVersion", "127");
      Map<String, Object> ltOptions = new HashMap<>();
      ltOptions.put("user", System.getenv("*******"));
      ltOptions.put("accessKey", System.getenv("*******"));
      ltOptions.put("build", "Selenium 4");
      ltOptions.put("name", "SampleTestOne");
      ltOptions.put("platformName", "Windows 10");
      ltOptions.put("seCdp", true);
      ltOptions.put("selenium_version", "4.23.0");
      capabilities.setCapability("LT:Options", ltOptions);

      driver = new RemoteWebDriver(new URL(hubURL), capabilities);
  }

  private static void test() {
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
  }

  public static void quitWebDriver(){
      if(driver != null){
          driver.quit();
      }
  }
}