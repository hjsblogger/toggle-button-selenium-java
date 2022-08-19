import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class ToggleButtonLambdaTest
{
    WebDriver driver = null;
    public static String status = "passed";
    String gridURL = "@hub.lambdatest.com/wd/hub";
    String user_name = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" :
            System.getenv("LT_USERNAME");
    String access_key = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" :
            System.getenv("LT_ACCESS_KEY");

    String testURL = "https://codepen.io/fidabrj/pen/NWYeaqG";
    private static final int MAX_COUNT = 6;
    String build_name;

    @BeforeTest
    @Parameters(value={"browser","version","platform", "resolution"})
    public void testSetUp(String browser, String version, String platform, String resolution) throws Exception
    {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability("browserName", browser);
        desiredCapabilities.setCapability("browserVersion", version);

        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", user_name);
        ltOptions.put("accessKey", access_key);
        ltOptions.put("platformName", platform);
        ltOptions.put("resolution", resolution);
        ltOptions.put("project", "Demonstration: Toggle Button on LambdaTest Grid");

        build_name = "Toggle - " + browser + " on " + platform + " combination";
        ltOptions.put("build", build_name);

        desiredCapabilities.setCapability("LT:Options", ltOptions);

        try
        {
            driver = new RemoteWebDriver(new URL("https://" + user_name + ":" +
                     access_key + gridURL), desiredCapabilities);
        }
        catch(MalformedURLException exc)
        {
            exc.printStackTrace();
        }
    }

    @Test(description="Demonstration of Toggle Buttons on LambdaTest Selenium Grid")
    public void test_toggle_buttons() throws InterruptedException
    {
        WebElement result_frame;
        WebElement toggle_button;
        String cssSelectorString;
        /* Keep a count of the buttons present on the page */
        Integer count = 1;

        driver.get(testURL);
        Thread.sleep(2000);

        /* Selenium Java 3.141.59 */
        WebDriverWait wait = new WebDriverWait(driver, 5);
        /* WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); */

        try
        {
            /* Switch to the iFrame using the ID locator */
            result_frame = driver.findElement(By.id("result"));
            driver.switchTo().frame(result_frame);

            /* Now that we are inside the iFrame, toggle all the buttons present in the iFrame */
            for (count = 1; count < MAX_COUNT; count++)
            {
                Thread.sleep(2000);
                cssSelectorString = "[for='cb" + count + "']";
                toggle_button = driver.findElement(By.cssSelector(cssSelectorString));
                toggle_button.click();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            status = "failed";
        }
    }

    @AfterTest
    public void tearDown()
    {
        if (driver != null)
        {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
