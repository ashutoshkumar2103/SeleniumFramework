package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//	This is the TestNG runner class used to execute Cucumber feature files along with step definitions.
//	We configure execution using @CucumberOptions where we define paths, reporting, and execution behavior.
//	1. Features: This specifies the path to the feature files that need to be executed.
//	2. Glue: This defines the package where step definition files and hooks are located.
//	3. DryRun: This checks whether all steps in the feature files have corresponding step definitions.
//	If set to true, it will not execute tests, only validate step mappings.
//	If false, it will execute the tests normally.
//	4. Monochrome: This makes the console output cleaner by removing unreadable characters (ANSI codes),
//	which is useful for better readability in logs and CI/CD tools.
//	5. Plugin: This is used to define output formats for reports and logs.
//	   Common plugins include:
//		- "pretty" for readable console output
//		- "html:target/report.html" for HTML report
//		- "json:target/report.json" for JSON report
//		- "junit:target/report.xml" for JUnit report
//	6. Tags: This is used to filter and run specific scenarios or features (e.g., @smoke, @regression),
//	which is important for selective test execution in real projects.

@CucumberOptions(
		features = "src/test/java/cucumber",
		glue = "stepDefinitions",
		dryRun = false,
		monochrome = true,
		plugin = {"html:target/cucumber-reports.html"}
		)
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

}
