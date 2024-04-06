package utils.runner;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import utils.LoggerUtils;

import java.util.Map;

public class BrowserManager {
    public static Browser createBrowser(Playwright playwright, Map<String, String> environment) {
        String browserName = environment.get("browser");
        boolean isHeadless = Boolean.parseBoolean(environment.get("isHeadless"));
        int slowMo = Integer.parseInt(environment.get("slowMo"));

        switch(browserName) {
            case "chromium" -> {

                return playwright.chromium()
                        .launch(new BrowserType.LaunchOptions()
                                .setHeadless(isHeadless)
                                .setSlowMo(slowMo));
            }
            case "firefox" -> {

                return playwright.firefox()
                        .launch(new BrowserType.LaunchOptions()
                                .setHeadless(isHeadless)
                                .setSlowMo(slowMo));
            }
            case "webkit" -> {

                return playwright.webkit()
                        .launch(new BrowserType.LaunchOptions()
                                .setHeadless(isHeadless)
                                .setSlowMo(slowMo));
            }
            default -> {
                LoggerUtils.logWarning("ERROR: " + browserName + "is NOT matched any options. Chromium is launched.");

                return playwright.chromium()
                        .launch(new BrowserType.LaunchOptions()
                                .setHeadless(true)
                                .setSlowMo(0));
            }
        }
    }
}
