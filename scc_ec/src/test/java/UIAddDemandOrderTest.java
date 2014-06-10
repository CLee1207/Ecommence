import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by zodiake on 2014/6/4.
 */
public class UIAddDemandOrderTest extends SeleneseTestBase {
    WebDriverBackedSelenium selenium;
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        String baseUrl = "http://localhost:8080/";
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Test
    public void createDemandOrder(){
        loginAs("tom","1");
        selenium.open("/buyerCenter/demandOrder?form");
        selenium.type("title", "title");
        selenium.type("exceptionAddress", "1");
        selenium.type("demandType", "1");
        selenium.type("receiveAddress","1");
        selenium.click("name=submit");
        verifyTrue(selenium.isTextPresent("title1"));
    }

    @Test
    public void createDemandOrderWithEmptyForm(){
        loginAs("tom","1");
        selenium.open("/buyerCenter/demandOrder?form");
        selenium.click("name=submit");
        verifyTrue(selenium.isTextPresent("不能为空"));
    }

    @Test
    public void updateDemandOrder(){
        loginAs("tom","1");
        selenium.open("/buyerCenter/demandOrder/1?edit");
        selenium.type("title", "title1");
        selenium.type("exceptionAddress", "1");
        selenium.type("demandType", "1");
        selenium.type("receiveAddress","1");
        selenium.click("name=submit");
    }

    private void loginAs(String userName, String password) {
        selenium.open("/buyerCenter/demandOrder?form");
        selenium.type("j_username", userName);
        selenium.type("j_password", password);
        selenium.click("name=submit");
        selenium.waitForPageToLoad("30000");
    }
}
