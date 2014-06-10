import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by zodiake on 2014/6/4.
 */
public class UIFabricTest extends SeleneseTestBase {
    WebDriverBackedSelenium selenium;
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        String baseUrl = "http://localhost:8080/";
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Test
    public void addFabric() {
        loginAs("tom", "1");
        selenium.open("/sellerCenter/fabricCreate");
        selenium.click("id=category1");
        selenium.click("id=source1");
        selenium.click("id=sourceDetail1");
        selenium.click("id=mainUseTypes2");
        selenium.click("name=_eventId_next");
        selenium.type("density","12");
        selenium.type("name","ab");
        selenium.type("keys","1");
        selenium.type("values","2");
        selenium.click("name=_eventId_finish");
        selenium.close();
    }

    private void loginAs(String userName, String password) {
        selenium.open("/buyerCenter/demandOrder?form");
        selenium.type("j_username", userName);
        selenium.type("j_password", password);
        selenium.click("name=submit");
        selenium.waitForPageToLoad("30000");
    }
}
