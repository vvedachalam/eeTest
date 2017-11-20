package com.ee.stepdef;

import com.ee.domain.HotelBookingsService;
import com.ee.pages.HotelBookingPage;
import com.ee.testdatabuilder.Customer;
import com.ee.testdatabuilder.CustomerBuilder;
import com.ee.util.Utils;
import com.jayway.restassured.response.Response;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

@ContextConfiguration("classpath:cucumber.xml")
public class HotelBookingStepDef {
    @Autowired
    public CustomerBuilder customerBuilder;
    @Autowired
    public Utils utils;

    @Autowired
    public HotelBookingsService hotelBookingsService;
    @Value("${test.url}")
    private String testUrl;
    @Autowired
    private HotelBookingPage hotelBookingPage;
    private Customer customer;

    @Given("^I am in the hotel booking homepage$")
    public void iAmInTheHotelBookingHomepage() throws Throwable {
        hotelBookingPage.navigateToBookingsHomePage(testUrl);

    }

    @When("^I set all valid details$")
    public void iSetAllValidDetails() throws Throwable {
        customer = customerBuilder.buildCustomerWithRandomValues();
        setCustomerDetails();

    }

    @And("^I save details$")
    public void iSaveDetails() throws Throwable {
        hotelBookingPage.saveDetails();
    }

    @Then("^I see all above details saved correctly$")
    public void iSeeAllAboveDetailsSavedCorrectly() throws Throwable {
        Assert.assertTrue(hotelBookingPage.getAllFirstnames().contains(customer.getFirstname()));

        Map<String, String> lastRowCustomerDetails = hotelBookingPage.getLastRowCustomerDetails();
        Assert.assertEquals("Firstname not saved correctly ", customer.getFirstname(), lastRowCustomerDetails.get("firstname"));
        Assert.assertEquals("Surname not saved correctly ", customer.getSurname(), lastRowCustomerDetails.get("lastname"));
        Assert.assertEquals("Price not saved correctly ", customer.getPrice(), lastRowCustomerDetails.get("totalprice"));
        Assert.assertEquals("Deposit paid not saved correctly ", customer.getDepositPaid(), lastRowCustomerDetails.get("depositpaid"));
        Assert.assertEquals("Check-in date not saved correctly ", customer.getCheckInDate(), lastRowCustomerDetails.get("checkin"));
        Assert.assertEquals("Check-out datenot saved correctly ", customer.getCheckOutDate(), lastRowCustomerDetails.get("checkout"));
    }

    @And("^I see all above details saved correctly in service$")
    public void iSeeAllAboveDetailsSavedCorrectlyInService() throws Throwable {
        Response bookingsResponse = hotelBookingsService.getBookings();
        List<Integer> bookingIdsList = bookingsResponse.path("bookingid");
        Response bookingDetailsResponse = hotelBookingsService.getBookingDetails(bookingIdsList.get(bookingIdsList.size() - 1));
        Assert.assertEquals(" Firstname not saved in service correctly ", customer.getFirstname(), bookingDetailsResponse.path("firstname"));
        Assert.assertEquals(" Surname not saved in service correctly ", customer.getSurname(), bookingDetailsResponse.path("lastname"));
        Assert.assertEquals(" Price not saved in service correctly ", Integer.parseInt(customer.getPrice()), bookingDetailsResponse.path("totalprice"));
        Assert.assertEquals("Deposit paid not saved in service correctly ", Boolean.parseBoolean(customer.getDepositPaid()), bookingDetailsResponse.path("depositpaid"));
        Assert.assertEquals("Check-in date not saved in service correctly ", customer.getCheckInDate(), bookingDetailsResponse.path("bookingdates.checkin"));
        Assert.assertEquals(" Check-out date not saved in service correctly ", customer.getCheckOutDate(), bookingDetailsResponse.path("bookingdates.checkout"));
    }

    @And("^I delete the last customer$")
    public void iDeleteTheLastCustomer() throws Throwable {
        hotelBookingPage.deleteLastCustomer();
    }

    @Then("^I see the customer has been deleted$")
    public void iSeeTheCustomerHasBeenDeleted() throws Throwable {
        Assert.assertFalse(hotelBookingPage.getAllFirstnames().contains(customer.getFirstname()));
        Map<String, String> lastRowCustomerDetails = hotelBookingPage.getLastRowCustomerDetails();
        Assert.assertNotEquals("Customer not been deleted ", customer.getFirstname(), lastRowCustomerDetails.get("firstname"));
        Assert.assertNotEquals("Customer not been deleted", customer.getSurname(), lastRowCustomerDetails.get("lastname"));
    }


    @And("^wait until saved$")
    public void waitUntilSaved() throws Throwable {
        hotelBookingPage.waitUntilCustomerSaved(customer.getFirstname());
        Response bookingsResponse = hotelBookingsService.getBookings();
        List<Integer> bookingIdsList = bookingsResponse.path("bookingid");
        customer.setBookingId(bookingIdsList.get(bookingIdsList.size() - 1));
    }

    @And("^wait until deleted$")
    public void waitUntilDeleted() throws Throwable {
        hotelBookingPage.waitUntilCustomerDeleted(customer.getFirstname());
    }

    @And("^I don't see the deleted item when I call service$")
    public void iDontSeeTheDeletedItemWhenICallService() throws Throwable {
        Response bookingsResponse = hotelBookingsService.getBookings();
        List<Integer> bookingIdsList = bookingsResponse.path("bookingid");
        Assert.assertFalse("Customer is not deleted", bookingIdsList.contains(customer.getBookingId()));
    }

    @When("^I set the below details$")
    public void iSetTheBelowDetails(DataTable customerDetailsTable) throws Throwable {
        List<String> cells = customerDetailsTable.getGherkinRows().get(0).getCells();

        String firstnameCellValue = cells.get(0);
        String firstname = "";
        String surname = "";
        if (!firstnameCellValue.equals("")) {
            firstname = firstnameCellValue + utils.getRandomString();
        }
        String surnameCellValue = cells.get(1);
        if (!surnameCellValue.equals("")) {
            surname = surnameCellValue + utils.getRandomString();
        }
        String price = cells.get(2);
        String depositPaid = cells.get(3);
        String checkInDate = cells.get(4);
        String checkOutDate = cells.get(5);
        hotelBookingPage.setFirstname(firstname);
        hotelBookingPage.setSurname(surname);
        hotelBookingPage.setPrice(price);
        hotelBookingPage.setDepositPaid(depositPaid);
        hotelBookingPage.setCheckInDate(checkInDate);
        hotelBookingPage.setCheckOutDate(checkOutDate);

        customer = new CustomerBuilder()
                .withFirstname(firstname)
                .withSurnameName(surname)
                .withPrice(price)
                .withDeposit(depositPaid)
                .withCheckInDate(checkInDate)
                .withCheckOutDate(checkOutDate)
                .build();
    }

//    private long getTimeStamp() {
//        return System.currentTimeMillis();
//    }

    @Then("^I see the customer details are not saved$")
    public void iSeeCustomerDetailsAreNotSaved() throws Throwable {
        Assert.assertFalse("Customer should not be saved", hotelBookingPage.getAllFirstnames().contains(customer.getFirstname()));
        Assert.assertFalse("Customer should not be saved", hotelBookingPage.getAllSurnames().contains(customer.getSurname()));

    }


    private void setCustomerDetails() {
        hotelBookingPage.setFirstname(customer.getFirstname());
        hotelBookingPage.setSurname(customer.getSurname());
        hotelBookingPage.setPrice(customer.getPrice());
        hotelBookingPage.setDepositPaid(customer.getDepositPaid());
        hotelBookingPage.setCheckInDate(customer.getCheckInDate());
        hotelBookingPage.setCheckOutDate(customer.getCheckOutDate());
    }

    @When("^I set all valid details other than checkin date$")
    public void iSetAllValidDetailsOtherThanCheckinDate() throws Throwable {
        customer = customerBuilder.buildCustomerWithRandomValues();
        customer.setCheckInDate("2017-11-99");
        setCustomerDetails();

    }

    @When("^I set all valid details other than checkout date$")
    public void iSetAllValidDetailsOtherThanCheckoutDate() throws Throwable {
        customer = customerBuilder.buildCustomerWithRandomValues();
        customer.setCheckOutDate("");
        setCustomerDetails();
    }

    @When("^I set all valid details other than empty firstname$")
    public void iSetAllValidDetailsOtherThanEmptyFirstname() throws Throwable {
        customer = customerBuilder.buildCustomerWithRandomValues();
        customer.setFirstname("");
        setCustomerDetails();

    }

    @When("^I set all valid details other than empty surname$")
    public void iSetAllValidDetailsOtherThanEmptySurname() throws Throwable {
        customer = customerBuilder.buildCustomerWithRandomValues();
        customer.setSurname("");
        setCustomerDetails();

    }

    @When("^I set all valid details other than price$")
    public void iSetAllValidDetailsOtherThanPrice() throws Throwable {
        customer = customerBuilder.buildCustomerWithRandomValues();
        customer.setPrice("xyz");
        setCustomerDetails();
    }

    @And("^wait for (\\d+) seconds$")
    public void waitForSeconds(int sec) throws Throwable {
        hotelBookingPage.waitForSeconds(sec);
    }
}
