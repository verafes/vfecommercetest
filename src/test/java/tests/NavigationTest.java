package tests;

import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import com.microsoft.playwright.*;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.TestData.BASE_URL;
import static utils.TestData.HOME_END_POINT;
import static utils.TestData.BASE_TITLE;

public class NavigationTest extends BaseTest {
    @Test
    public void testBaseUrlLaunch() {
        assertThat(page).hasURL(BASE_URL + HOME_END_POINT);
    }

    @Test
    public void testHomePage_URLAndTitle_AsExpected() {
        assertThat(getPage()).hasURL(BASE_URL + HOME_END_POINT);
        assertThat(getPage()).hasTitle(BASE_TITLE);
    }

    @Test(dependsOnMethods = "testBaseUrlLaunch")
    public void testWomenMenu_NavigatesToWomenPage() {
        page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions()
                .setName("Women".toLowerCase()).setExact(true)
        ).click();

        assertThat(page).hasTitle(TestData.WOMEN_TITLE);
        assertThat(page).hasURL(BASE_URL + TestData.WOMEN_END_POINT);
    }
}
