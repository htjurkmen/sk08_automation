package lecturetest.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import lecturetest.example.pages.Header;
import lecturetest.example.pages.HomePage;
import lecturetest.example.pages.LoginPage;
import lecturetest.example.pages.ProfilePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;

    @BeforeSuite
    public void setUpSuite() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setupDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

    }

    @DataProvider(name = "users")
    public Object[][] getUsers() {
        return new Object[][]{
                {"auto_user", "auto_pass", "auto_user"}, // test login by username
                {"auto_user1@abv.bg", "auto_pass", "auto_user1"}, // test login by email
        };
    }

    @Test(dataProvider = "users")
    public void LoginTest(String  userNameOrEmail, String pass, String username) {
        System.out.println("1. Navigate to home page");
        HomePage homePage = new HomePage(driver);
        homePage.navigate();

        System.out.println("2. Navigate to login");
        Header header = new Header(driver);
        header.goToLogin();


        System.out.println("3. Check the correct url is opened (login)");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyUrl();
        System.out.println("4. Check that Sign in header is displayed");

        String headerText = loginPage.getSignInHeaderText();
        Assert.assertEquals(headerText, "Sign in", "Incorect Text ");

        System.out.println("5. Enter username");
        loginPage.enterUsername("auto_user");

        System.out.println("6. Enter password");
        loginPage.enterPassword("auto_pass");

        System.out.println("7. Click Sign in button");
        loginPage.clickSignIn();

        System.out.println("8. Check the correct url is opened (homepage)");
        homePage.verifyUrl();

        System.out.println("9. Navigate to Profile page");
        header.goToProfile();

        System.out.println("10. Check the correct url is opened (profile)");
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.verifyUrl();

        System.out.println("11. Check that the username is displayed correctly");

        String usernameHEaderText = profilePage.getUsernameHeaderText();
        Assert.assertEquals(usernameHEaderText, "auto_user", "Incorrect ");


    }

    @AfterMethod
    public void cleanUp() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
