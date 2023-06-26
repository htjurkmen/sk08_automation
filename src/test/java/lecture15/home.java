package lecture15;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

//0.load main URL
//1.check login btn and click it
//2.check "Sign in"
//3.Enter valid username
//4.Enter valid password
//5.Check(active/displayed) and click - Remember me
//6.Check Sign in button active and click it
//7.Check successful log in
//8.Check logout button and click logout from homePage
//9.click logout from profilePage
//10.click logout from newPost
//11.Check successful logout
//12.Check logoutbtn is not visible after logout

public class home {
    private static ChromeDriver driver;
    private final String BASE_URL = "http://training.skillo-bg.com/";
    private final String HOME_URL = BASE_URL + "posts/all";
    private final String LOGOUT_URL = BASE_URL + "users/login";

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        driver.get(HOME_URL);
    }
    @Test
    public void setLOGOUTHomePage() {
        System.out.println("1.check login btn and click it");
        WebElement loginBtn = driver.findElement(By.id("nav-link-login"));
        clickElement(loginBtn);

        System.out.println("2.check 'Sign in' ");
        WebElement SignBtn = driver.findElement(By.xpath("//p[text()='Sign in']"));
        Assert.assertTrue(SignBtn.isDisplayed(), "Sign Btn icon is not visible");

        System.out.println("3.Enter valid username");
        WebElement username = driver.findElement(By.id("defaultLoginFormUsername"));
        username.sendKeys("auto_user");

        System.out.println("4.Enter valid password");
        WebElement pass = driver.findElement(By.id("defaultLoginFormPassword"));
        pass.sendKeys("auto_pass");

        System.out.println("5.Check(active/displayed) and click - Remember me");
        WebElement rememberBtn = driver.findElement(By.cssSelector(".remember-me-button"));
        rememberBtn.click();
        Assert.assertTrue(rememberBtn.isSelected(), "Btn is selected");

        System.out.println("6.Check Sign in button active and click it");
        WebElement signInBtn = driver.findElement(By.id("sign-in-button"));
        Assert.assertTrue(signInBtn.isEnabled(), "Btn is enabled");
        signInBtn.click();

        System.out.println("7.Check successful log in");
        WebElement successfulLogIn = driver.findElement(By.id("toast-container"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(successfulLogIn));
        String toastMsg = successfulLogIn.getText().trim();
        Assert.assertEquals(toastMsg, "Successful login!", "Login is not successful: " + toastMsg);


        System.out.println("8.Check logout button and click it in the homepage");
        WebElement logOutBtn = driver.findElement(By.cssSelector(".fa-sign-out-alt"));
        Assert.assertTrue(logOutBtn.isDisplayed(), "logOutBtn icon is not visible");
        logOutBtn.click();

        System.out.println("11.Check successful logout");
        WebElement successfulLogout = driver.findElement(By.xpath("//div[@class=\"toast-success ngx-toastr ng-trigger ng-trigger-flyInOut\"]"));

        wait.until(ExpectedConditions.visibilityOf(successfulLogout));
        String toastMsg2 = successfulLogout.getText().trim();
        Assert.assertEquals(toastMsg2, "Successful logout!", "Login is not successful: " + toastMsg2);

        System.out.println("12.Check logoutbtn is not visible after logout");
        WebElement logOutBtn1 = driver.findElement(By.cssSelector(".fa-sign-out-alt"));
        Assert.assertTrue(logOutBtn1.isDisplayed(), "logOutBtn icon is not visible");

    }

    @Test
    public void logOutNewPost() {
        WebElement loginBtn = driver.findElement(By.id("nav-link-login"));
        clickElement(loginBtn);

        WebElement username = driver.findElement(By.id("defaultLoginFormUsername"));
        username.sendKeys("auto_user");

        WebElement pass = driver.findElement(By.id("defaultLoginFormPassword"));
        pass.sendKeys("auto_pass");

        WebElement signInBtn = driver.findElement(By.id("sign-in-button"));
        Assert.assertTrue(signInBtn.isEnabled(), "Btn is enabled");
        signInBtn.click();

        System.out.println("9.click logout from profilePage");
        WebElement profilePage = driver.findElement(By.id("nav-link-profile"));
        WebElement logOutBtn = driver.findElement(By.cssSelector(".fa-sign-out-alt"));
        profilePage.click();
        logOutBtn.click();

    }
    @Test
    public void profilePageLogOut() {
        WebElement loginBtn = driver.findElement(By.id("nav-link-login"));
        clickElement(loginBtn);

        WebElement username = driver.findElement(By.id("defaultLoginFormUsername"));
        username.sendKeys("auto_user");

        WebElement pass = driver.findElement(By.id("defaultLoginFormPassword"));
        pass.sendKeys("auto_pass");

        WebElement signInBtn = driver.findElement(By.id("sign-in-button"));
        Assert.assertTrue(signInBtn.isEnabled(), "Btn is enabled");
        signInBtn.click();

        System.out.println("10.click logout from newPost");
        WebElement newPost = driver.findElement(By.id("nav-link-new-post"));
        WebElement logOutBtn = driver.findElement(By.cssSelector(".fa-sign-out-alt"));
        newPost.click();
        logOutBtn.click();
    }
    private void clickElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    private void populateField(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
        String classes = element.getAttribute("class");
        Assert.assertTrue(classes.contains("is-valid"), "The field is not valid!");
    }

    @AfterMethod
    public void cleanup() {
        driver.close();
    }

}