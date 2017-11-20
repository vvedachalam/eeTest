Getting Started
===============

* Install JDK 1.8 and set up with environment variable 
* Set up Maven 3 and set up with environment variable
* Download latest (Tested with chrome 2.29, Safari. Firefox Gecodriver fails for some reason) drivers  to below path ${project.base.dir}\src\test\resources\config\drivers_exe 
* Please change each driver extentions in folder drivers_exe form .txt to exe, I've changed them to .txt because I can't email .exe files. If any problem please download above mentioned drivers to drivers_exe then run the tests
 
 1. We no need to download safaridriver as it available at "/usr/bin/safaridriver"
 2. chromedriver.exe
 3. geckodriver.exe

How Run Tests
=============
Tests can be run with Maven or with Intellij

1.Run tests with maven
---------------
 Go to project directory where pom.xml located, open command prompt
 
 a. Then run below maven command (This is default command and kicks off Chromedriver)
 ```
 mvn clean install
 ```
 b. 2 tests fail and marked as bugs. To exclude those test and get 100% success, use
```
 mvn clean test -Dcucumber.options="--tags @hotelbooking --tags ~@bug"
```


 To Run with safari
 ```
  mvn clean install -Dbrowser.name=safari
  ```

Run with Firefox
 ```
  mvn clean install -Dbrowser.name=firefox
  ```
  
Run with Internet explorer
  ```
   mvn clean install -Dbrowser.name=ie
   ```

  
  2.Run tests with intellij
  ---------------
The IntelliJ Cucumber Java runner works better than its JUnit runner for running Gherkin features.
We can jump between the test runner pane and the Gherkin definitions & can run individual scenarios
rather the entire feature file.

To set this up, open the Edit Run Configurations dialog.

Add the following to the defaults for Cucumber Java tests:

Main Class: cucumber.api.cli.Main
Glue: com.ee

Now We can run feature files from the right-button popup menu.
