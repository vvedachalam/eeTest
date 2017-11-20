package com.ee.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class BrowserFactory {

  //    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverHelper.class);
  private final static String INTERNET_EXPLORER = "internet explorer";
  private final static String IE = "ie";
  private final static String SAFARI = "safari";
  private final static String CHROME = "chrome";
  private final static String FIREFOX = "firefox";
  private final static String WEBDRIVER_MODE_LOCAL = "local";
  private final static String WEBDRIVER_MODE_GRID = "grid";
  @Value("${webdriver.mode}")
  private String driverMode;
  @Value("${webdriver.hubURL}")
  private String hubUrl;
  @Value("${browser.name}")
  private String browserName;
  @Value("${browser.version}")
  private String browserVersion;
  @Value("${browser.platform}")
  private String browserPlatform;

  public BrowserDriver create() {
    WebDriver driver = getDriverObject(driverMode);
    if (driver == null) {
      throw new IllegalArgumentException("Mode: " + driverMode + " - Unsupported webdriver: " + browserName + " " +
                                         browserVersion + " " + browserPlatform);
    }
    return new BrowserDriver(driver);
  }

  private WebDriver getDriverObject(String driverMode) {
    WebDriver driver = null;

    String extension;
    if (System.getProperty("os.name").contains("Mac OS")) {
      extension = "";
    } else {
      extension = ".exe";
    }
    System.out.println("extension = [" + extension + "]");

    if (WEBDRIVER_MODE_LOCAL.equals(driverMode)) {
      System.out.println(System.getProperty("os.name"));
      String driversPath = System.getProperty("user.dir") + "/src/test/resources/config/drivers_exe/";
      switch (browserName) {
        case FIREFOX:

          System.setProperty("webdriver.gecko.driver", driversPath + "geckodriver" + extension);
          driver = new FirefoxDriver();
          break;
//  if ie or internet explorer both should work, switch-case fall through by omitting the break;
// statement.
        case INTERNET_EXPLORER:
        case IE:
          System.setProperty("webdriver.ie.driver", driversPath + "IEDriverServer" + extension);
          driver = new InternetExplorerDriver();
          break;
// Unexpected error launching Internet Explorer. Browser zoom level was set to 85%. It should be set to 100%
// Do this by open IE browser and press CTRL+0, sets browser to 100%
        case CHROME:
          System.setProperty("webdriver.chrome.driver", driversPath + "chromedriver" + extension);
          System.out.print(System.getProperty("webdriver.chrome.driver"));
          driver = new ChromeDriver();
          break;
        case SAFARI:
          System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver" + extension);
          System.out.print(System.getProperty("webdriver.chrome.driver"));
          driver = new SafariDriver();
          break;
        default:
          throw new RuntimeException("Invalid Browser Name");
      }
      driver.manage().window().maximize();

    } else if (WEBDRIVER_MODE_GRID.equals(driverMode)) {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(CapabilityType.PLATFORM, browserPlatform);
      capabilities.setCapability(CapabilityType.VERSION, browserVersion);
      capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);

      if (browserName.equals(INTERNET_EXPLORER)) {
        //Below setting has been done because of some time getting an error in IE8 as below
        //Unexpected error launching Internet Explorer. Browser zoom level was set to 109%. It should be set to 100%
        capabilities.setCapability("ignoreZoomSetting", true);
      }
      driver = new RemoteWebDriver(createUrl(hubUrl), capabilities);
      driver.manage().window().maximize();
    }

    return driver;
  }

  private URL createUrl(final String gridHubUrl) {
    try {
      return new URL(gridHubUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
