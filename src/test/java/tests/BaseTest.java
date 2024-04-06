package tests;

import com.microsoft.playwright.*;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.LoggerUtils;

import static utils.TestData.BASE_URL;


public abstract class BaseTest  {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = playwright.chromium().launch(new BrowserType.
            LaunchOptions().setHeadless(false).setSlowMo(1500));
    BrowserContext context = browser.newContext();
    Page page = browser.newPage();

    @BeforeSuite
    protected void beforeSuite() {
//        System.setProperty("log4j2.debug", "true");
        if (playwright != null) {
            LoggerUtils.logInfo("Playwright created");
            Reporter.log("-------Playwright is created");
        } else {
//            System.out.println("FATAL: Playwright is NOT created");
            Reporter.log("FATAL: Playwright is NOT created");
            System.exit(1);
        }

        if(browser.isConnected()) {
            Reporter.log("Browser is created");
        } else {
            Reporter.log("FATAL: Browser is NOT created");
            System.exit(1);
        }
    }

    @BeforeMethod
    protected void beforeMethod() {
        context = browser.newContext();
        Reporter.log("Context created");

        page = context.newPage();
        Reporter.log("Page created");

        page.waitForTimeout(1000);
        page.navigate(BASE_URL);
    }

    @AfterMethod
    protected void closeContext() {
        if (page != null) {
            page.close();
            Reporter.log("Page closed");
        }
        if (context != null) {
            context.close();
            Reporter.log("Context closed");
        }
    }

    @AfterSuite
    protected void closeBrowserAndPlaywright() {
        if (browser != null) {
            browser.close();
            Reporter.log("Browser closed");
        }
        if (playwright != null) {
            playwright.close();
            Reporter.log("Playwright closed");
        }
    }

    public Page getPage() {
        return page;
    }

    public void setTestId(String id) {
        playwright.selectors().setTestIdAttribute(id);
    }

    public Locator getId(String testId) {
        setTestId("id");
        return page.getByTestId(testId);
    }
}
