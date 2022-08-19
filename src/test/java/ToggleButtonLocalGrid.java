import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

public class ToggleButtonLocalGrid
{
    WebDriver driver = null;
    public static String status = "passed";
    private static final int MAX_COUNT = 6;

    String testURL = "https://codepen.io/fidabrj/pen/NWYeaqG";

    @BeforeTest
    public void testSetUp() throws Exception
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(description="Demonstration of Toggle Buttons on local Selenium Grid")
    public void test_toggle_buttons() throws InterruptedException
    {
        WebElement result_frame;
        WebElement toggle_button;
        String cssSelectorString;
        /* Keep a count of the buttons present on the page */
        Integer count = 1;

        driver.get(testURL);
        Thread.sleep(5000);

        /* Selenium Java 3.141.59 */
        WebDriverWait wait = new WebDriverWait(driver, 5);
        /* WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); */

        /* Switch to the iFrame using the ID locator */
        result_frame = driver.findElement(By.id("result"));
        driver.switchTo().frame(result_frame);

        /* Now that we are inside the iFrame, toggle all the buttons present in the iFrame */
        for (count = 1; count < MAX_COUNT; count++)
        {
            Thread.sleep(5000);
            cssSelectorString = "[for='cb" + count + "']";
            toggle_button = driver.findElement(By.cssSelector(cssSelectorString));
            toggle_button.click();
        }
    }

    @AfterTest
    public void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }
}
