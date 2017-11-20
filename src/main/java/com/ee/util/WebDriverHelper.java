package com.ee.util;

import com.ee.context.Context;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebDriverHelper {
  @Autowired
  private Context context;

  public void navigateToURL(final String url) {
    context.getBrowser().get(url);
  }

  public List<WebElement> getElements(final By locator) {
    return context.getBrowser().findElements(locator);
  }

  public void clickOnElement(final By locator) {
    context.getBrowser().findElement(locator).click();
  }

  public void selectValueFromDropdown(final By locator, final String selectingValue) {
    final WebElement option = context.getBrowser().findElement(locator);
    final Select selectOption = new Select(option);
    selectOption.selectByVisibleText(selectingValue);
  }

  public void setTextInTextbox(final By locator, final String text) {
    context.getBrowser().findElement(locator).clear();
    context.getBrowser().findElement(locator).sendKeys(text);
  }

  public void waitForElementToPresent(final By element, final int waitTime) {
    try {
      (new WebDriverWait(context.getBrowser(), waitTime))
          .until(ExpectedConditions.presenceOfElementLocated(element));
    } catch (TimeoutException te) {
//            if catches time out exception do nothing

    }
  }

  public void screenshot() {
    byte[] screenshot = context.getBrowser().getScreenshotAs(OutputType.BYTES);
    context.getScenario().embed(screenshot, "image/png");
  }

}
