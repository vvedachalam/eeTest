package com.ee.browser;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.Set;

public class BrowserDriver implements WebDriver, JavascriptExecutor, TakesScreenshot {
  private final WebDriver webDriver;
  private final JavascriptExecutor javascriptExecutor;
  private final TakesScreenshot takeScreenshot;

  public BrowserDriver(WebDriver driver) {
    this.webDriver = driver;
    this.javascriptExecutor = driver instanceof JavascriptExecutor ? (JavascriptExecutor) driver : null;
    // this takes into consideration the remote webdriver!
    if (driver.getClass().equals(RemoteWebDriver.class)) {
      takeScreenshot = (TakesScreenshot) new Augmenter().augment(driver);
    } else if (driver instanceof TakesScreenshot) {
      this.takeScreenshot = (TakesScreenshot) driver;
    } else {
      this.takeScreenshot = null;
    }
  }

  @Override
  public void close() {
    this.webDriver.close();
  }

  @Override
  public WebElement findElement(By arg0) {
    return this.webDriver.findElement(arg0);
  }

  @Override
  public List<WebElement> findElements(By arg0) {
    return this.webDriver.findElements(arg0);
  }

  @Override
  public void get(String arg0) {
    this.webDriver.get(arg0);
  }

  @Override
  public String getCurrentUrl() {
    return this.webDriver.getCurrentUrl();
  }

  @Override
  public String getPageSource() {
    return this.webDriver.getPageSource();
  }

  @Override
  public String getTitle() {
    return this.webDriver.getTitle();
  }

  @Override
  public String getWindowHandle() {
    return this.webDriver.getWindowHandle();
  }

  @Override
  public Set<String> getWindowHandles() {
    return this.webDriver.getWindowHandles();
  }

  @Override
  public Options manage() {
    return this.webDriver.manage();
  }

  @Override
  public Navigation navigate() {
    return this.webDriver.navigate();
  }

  @Override
  public void quit() {
    this.webDriver.quit();
  }

  @Override
  public TargetLocator switchTo() {
    return this.webDriver.switchTo();
  }

  @Override
  public Object executeAsyncScript(String arg0, Object... arg1) {
    return this.javascriptExecutor.executeAsyncScript(arg0, arg1);
  }

  @Override
  public Object executeScript(String arg0, Object... arg1) {
    return this.javascriptExecutor.executeScript(arg0, arg1);
  }

  @Override
  public <X> X getScreenshotAs(OutputType<X> arg0) throws WebDriverException {
    return this.takeScreenshot.getScreenshotAs(arg0);
  }

  /**
   * {@inheritDoc}.
   */

  public WebDriver getDriver() {
    return this.webDriver;
  }

}
