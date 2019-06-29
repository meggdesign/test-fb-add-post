import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestFb {
    private WebDriver driver;
    String login = "";
    String password = "";


    @BeforeTest
    public void setUp(){
        ChromeDriverManager.getInstance().setup();
        driver=new ChromeDriver();

        String url="https://www.facebook.com/";
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void fbTest(){

        WebElement usernameField=driver.findElement(By.id("email"));
        WebElement passwordField=driver.findElement(By.id("pass"));
        WebElement loginButton=driver.findElement(By.id("u_0_2"));

        usernameField.clear();
        usernameField.sendKeys(login);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();

        Assert.assertTrue(driver.getPageSource().contains("Strona główna"));

    }

    @Test (dependsOnMethods = "fbTest")
    public void status() throws InterruptedException {

        WebElement statusField = driver.findElement(By.name("xhpc_message"));
        statusField.sendKeys("Hello world");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement shareButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("._1mf7._4jy0._4jy3._4jy1._51sy.selected._42ft")));
        shareButton.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        Assert.assertTrue(driver.getPageSource().contains("Hello world"));

    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
