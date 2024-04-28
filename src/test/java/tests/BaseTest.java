package tests;

import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.LoggerUtils;
import utils.ReportUtils;
import utils.runner.BrowserManager;
import utils.runner.ConfigProperties;

import java.lang.reflect.Method;

import static utils.helpers.TestData.BASE_URL;
import static utils.helpers.TestData.HOME_END_POINT;


public abstract class BaseTest  {
    private final Playwright playwright = Playwright.create();
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeSuite
    void checkIfPlaywrightCreated() {
        ReportUtils.logReportHeader();

        if (playwright != null) {
            LoggerUtils.logInfo("Playwright is created");
        } else {
            LoggerUtils.logFatal("FATAL: Playwright is NOT created");
            System.exit(1);
        }

        LoggerUtils.logInfo(ReportUtils.printLine());
    }

     @BeforeClass
     @Parameters({"browserOption", "isHeadless", "slowMo"})
     void launchBrowser(String browserOption, String isHeadless, String slowMo) {
        this.browser = BrowserManager.createBrowser(playwright, browserOption, isHeadless, slowMo);
        // this.browser = BrowserManager.createBrowser(playwright, ConfigProperties.ENVIRONMENT_CHROMIUM);

        if(browser.isConnected()) {
            LoggerUtils.logInfo(ReportUtils.printLine());
            LoggerUtils.logInfo("Browser " + browser.browserType().name() + " is connected. \n\n");
            LoggerUtils.logInfo(ReportUtils.printLine());
        } else {
            LoggerUtils.logInfo(ReportUtils.printLine());
            LoggerUtils.logFatal("FATAL: Browser is NOT connected");
            System.exit(1);
        }
    }

    @BeforeMethod
    void createContextAndPage(Method method) {
        ReportUtils.logTestName(method);

        context = browser.newContext();
        LoggerUtils.logInfo("Context created");

        page = context.newPage();
        LoggerUtils.logInfo("Page created");

        LoggerUtils.logInfo("Start tests");

        page.waitForTimeout(1000);
        page.navigate(BASE_URL);

        if(isOnHomePage()) {
            LoggerUtils.logInfo("Base url is opened and content is not empty.");
        } else {
            LoggerUtils.logInfo("ERROR: Base url is NOT opened OR content is EMPTY.");
        }
    }

    @AfterMethod
    void closeContext(ITestResult result, Method method) {
        ReportUtils.logTestResult(method, result);

        if (page != null) {
            page.close();
            LoggerUtils.logInfo("Page closed");
        }
        if (context != null) {
            context.close();
            LoggerUtils.logInfo("Context closed\n");
        }
    }

    @AfterClass
    void closeBrowser(){
        if (browser != null && browser.isConnected()) {
            browser.close();
            if(!browser.isConnected()) {
                LoggerUtils.logInfo(ReportUtils.printLine());
                LoggerUtils.logInfo("Browser " + browser.browserType().name() + " is closed");
                LoggerUtils.logInfo(ReportUtils.printLine());
            }
        }
    }

    @AfterSuite
    void closeBrowserAndPlaywright() {
        if (browser != null) {
            browser.close();
            LoggerUtils.logInfo("Browser closed");
        }
        if (playwright != null) {
            playwright.close();
            LoggerUtils.logInfo("Playwright closed"
                    + ReportUtils.printLine());
        }
    }

    public Page getPage() {

        return page;
    }

    private boolean isOnHomePage() {
        getPage().waitForLoadState();

        return getPage().url().equals(BASE_URL + HOME_END_POINT) && !page.content().isEmpty();
    }

    protected boolean getIsOnHomePage() {

        return isOnHomePage();
    }

    public void setTestId(String id) {
        playwright.selectors().setTestIdAttribute(id);
    }

    public Locator getId(String testId) {
        setTestId("id");

        return page.getByTestId(testId);
    }
}
