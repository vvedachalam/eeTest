package com.ee.testdatabuilder;

import com.ee.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerBuilder {
  @Autowired
  public Utils utils;

  protected String checkInDate;
  protected boolean isCheckInDateSet = false;

  protected String checkOutDate;
  protected boolean isCheckOutDateSet = false;

  protected String deposit;
  protected boolean isDepositSet = false;

  protected String firstname;
  protected boolean isFirstnameSet = false;

  protected String price;
  protected boolean isPriceSet = false;

  protected String surname;
  protected boolean isSurnameSet = false;


  public CustomerBuilder withCheckInDate(String value) {
    this.checkInDate = value;
    this.isCheckInDateSet = true;
    return this;
  }

  public CustomerBuilder withCheckOutDate(String value) {
    this.checkOutDate = value;
    this.isCheckOutDateSet = true;
    return this;
  }

  public CustomerBuilder withDeposit(String value) {
    this.deposit = value;
    this.isDepositSet = true;
    return this;
  }

  public CustomerBuilder withFirstname(String value) {
    this.firstname = value;
    this.isFirstnameSet = true;
    return this;
  }

  public CustomerBuilder withPrice(String price) {
    this.price = price;
    this.isPriceSet = true;
    return this;
  }

  public CustomerBuilder withSurnameName(String value) {
    this.surname = value;
    this.isSurnameSet = true;
    return this;
  }

  public Customer build() {
    Customer customer = null;
    try {
      customer = new Customer();

      if (isFirstnameSet) {
        customer.setFirstname(firstname);
      }
      if (isSurnameSet) {
        customer.setSurname(surname);
      }
      if (this.isPriceSet) {
        customer.setPrice(this.price);
      }

      if (this.isCheckInDateSet) {
        customer.setCheckInDate(this.checkInDate);
      }
      if (this.isCheckOutDateSet) {
        customer.setCheckOutDate(this.checkOutDate);
      }
      if (this.isDepositSet) {
        customer.setDeposit(this.deposit);
      }
      return customer;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Throwable t) {
      throw new java.lang.reflect.UndeclaredThrowableException(t);
    }
  }

  public Customer buildCustomerWithRandomValues() {
    Customer customer = new CustomerBuilder()
        .withSurnameName(getRandomSurname())
        .withFirstname(getRandomFirstName())
        .withDeposit("true")
        .withCheckInDate("2017-11-28")
        .withCheckOutDate("2017-11-29")
        .withPrice("1000")
        .build();
    return customer;
  }

  private String getRandomFirstName() {
    return "Vijay" + utils.getRandomString();
  }

  private String getRandomSurname() {
    return "Veda" + utils.getRandomString();
  }

}