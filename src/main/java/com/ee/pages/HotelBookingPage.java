package com.ee.pages;

import com.ee.context.Context;
import com.ee.util.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

@Component
public class HotelBookingPage {
    private final By LAST_SAVED_ROW_IN_BOOKING_FORM_SELECTOR = By.cssSelector("#bookings [id][class='row']:last-child p");
    private final By ALL_SAVED_ROWS_IN_BOOKING_FORM_SELECTOR = By.cssSelector("#bookings [id][class='row']");
    private final By ALL_FIRSTNAMES_SELECTOR = By.cssSelector("#bookings [id][class='row'] div:first-child p");
    private final By ALL_SURNAMES_SELECTOR = By.cssSelector("#bookings [id][class='row'] div:first-child + div p");
    private final By LAST_ROW_DELETE_BUTTON_SELECTOR = By.cssSelector("#bookings [id][class='row']:last-child [value='Delete']");
    private final By FIRSTNAME_SELECTOR = By.id("firstname");
    private final By SURNAME_SELECTOR = By.id("lastname");
    private final By TOTAL_PRICE_SELECTOR = By.id("totalprice");
    private final By DEPOSIT_PAID_SELECTOR = By.id("depositpaid");
    private final By CHECK_IN_DATE = By.id("checkin");
    private final By CHECK_OUT_DATE = By.id("checkout");
    private final By SAVE_DETAILS_BUTTON_SELECTOR = By.cssSelector("#form div input[value=' Save '][type='button']");
    @Autowired
    public Context context;
    @Autowired
    private WebDriverHelper webDriverHelper;

    public void navigateToBookingsHomePage(String url) {
        webDriverHelper.navigateToURL(url);
    }

    public Map<String, String> getLastRowCustomerDetails() {
        webDriverHelper.waitForElementToPresent(LAST_SAVED_ROW_IN_BOOKING_FORM_SELECTOR, 5);
        List<WebElement> elements = webDriverHelper.getElements(LAST_SAVED_ROW_IN_BOOKING_FORM_SELECTOR);
        Map<String, String> customerMap = new HashMap<>();
        if (elements.size() > 0) {
            customerMap.put("firstname", elements.get(0).getText());
            customerMap.put("lastname", elements.get(1).getText());
            customerMap.put("totalprice", elements.get(2).getText());
            customerMap.put("depositpaid", elements.get(3).getText());
            customerMap.put("checkin", elements.get(4).getText());
            customerMap.put("checkout", elements.get(5).getText());
        }

        return customerMap;
    }


    public void setFirstname(String firstname) {
        webDriverHelper.waitForElementToPresent(FIRSTNAME_SELECTOR, 5);
        webDriverHelper.setTextInTextbox(FIRSTNAME_SELECTOR, firstname);
    }

    public void setSurname(String surname) {
        webDriverHelper.setTextInTextbox(SURNAME_SELECTOR, surname);
    }

    public void setPrice(String price) {
        webDriverHelper.setTextInTextbox(TOTAL_PRICE_SELECTOR, price);
    }

    public void setDepositPaid(String depositPaid) {
        webDriverHelper.selectValueFromDropdown(DEPOSIT_PAID_SELECTOR, depositPaid);
    }

    public void setCheckInDate(String checkInDate) {
        webDriverHelper.setTextInTextbox(CHECK_IN_DATE, checkInDate);
    }

    public void setCheckOutDate(String checkOutDate) {
        webDriverHelper.setTextInTextbox(CHECK_OUT_DATE, checkOutDate);
    }

    public void saveDetails() {
        webDriverHelper.clickOnElement(SAVE_DETAILS_BUTTON_SELECTOR);
    }

    public void deleteLastCustomer() {
        webDriverHelper.clickOnElement(LAST_ROW_DELETE_BUTTON_SELECTOR);
    }


    public void waitUntilTextAppearOrDisappear(final String text, int waitInSeconds, final boolean textAppear) {
//        when we are adding/deleting new rows DOM is keep changing and getting StaleElementReferenceException
//        simple way of overcome this problem we are trying catch exception and carry on running the tests
        try {
            (new WebDriverWait(context.getBrowser(), waitInSeconds)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    if (d.findElements(ALL_SAVED_ROWS_IN_BOOKING_FORM_SELECTOR).size() > 0) {
                        return d.findElements(LAST_SAVED_ROW_IN_BOOKING_FORM_SELECTOR).get(0).getText().equals(text) == textAppear;
                    } else {
                        return null;
                    }

                }
            });
        } catch (StaleElementReferenceException sere) {
//            when exception caught carry on
        }

    }

    public void waitForSeconds(int sec) throws InterruptedException {
        sleep( sec*1000);
    }

    public void waitUntilCustomerSaved(String firstName) {
        webDriverHelper.waitForElementToPresent(LAST_SAVED_ROW_IN_BOOKING_FORM_SELECTOR, 10);
        waitUntilTextAppearOrDisappear(firstName, 20, true);
    }

    public void waitUntilCustomerDeleted(String firstName) {
        webDriverHelper.waitForElementToPresent(LAST_SAVED_ROW_IN_BOOKING_FORM_SELECTOR, 10);
        waitUntilTextAppearOrDisappear(firstName, 10, false);
    }

    public List<String> getAllFirstnames() {
        return getTextFromWebElements(ALL_FIRSTNAMES_SELECTOR);

    }

    public List<String> getAllSurnames() {
        return getTextFromWebElements(ALL_SURNAMES_SELECTOR);

    }

    private List<String> getTextFromWebElements(By locator) {
        List<String> stringList = new ArrayList<>();
        List<WebElement> elements = webDriverHelper.getElements(locator);
        for (WebElement element : elements) {
            stringList.add(element.getText());
        }
        return stringList;
    }
}


