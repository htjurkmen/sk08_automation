package lecturetest.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final String BASE_URL = "http://training.skillo-bg.com:4200/users/";
    private final WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".profile-user-settings > h2")
    WebElement usernameHeader;

    public ProfilePage(WebDriver driver) {
        this.driver= driver;
      //  PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    public String getUsernameHeaderText() {
        wait.until(ExpectedConditions.visibilityOf(usernameHeader));
        return  usernameHeader.getText();

    }

    public void verifyUrl() {
        wait.until(ExpectedConditions.urlContains(BASE_URL));

    }
}
