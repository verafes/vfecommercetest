package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import org.testng.Assert;
import utils.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.helpers.TestData.BASE_URL;
import static utils.helpers.TestData.HOME_END_POINT;
import static utils.helpers.TestData.BASE_TITLE;

public class NavigationTest extends BaseTest {
    @Test
    public void TestBaseUrlLanding() {
        assertThat(getPage()).hasURL(BASE_URL + HOME_END_POINT);
    }

    @Test
    public void testHomePageURLAndTitleAsExpected() {
        assertThat(getPage()).hasURL(BASE_URL + HOME_END_POINT);
        assertThat(getPage()).hasTitle(BASE_TITLE);
    }

    @Test(
            dataProvider =  "NavigationBarTestData",
            dataProviderClass = TestData.class
    )
    public void testMenuNavigatesToCorrespondingPage(
            String menu, String correspondingPageTitle, String correspondingPageEndPoint
    ) {
        if (getIsOnHomePage()) {
            getPage().getByRole(
                    AriaRole.LINK,
                    new Page.GetByRoleOptions().setName(menu.toLowerCase()).setExact(true)
            ).click();

            assertThat(getPage()).hasTitle(correspondingPageTitle);
            assertThat(getPage()).hasURL(correspondingPageEndPoint);

        } else {
            Assert.fail();
        }
    }

    @Test(dependsOnMethods = "TestBaseUrlLanding")
    public void testWomenMenuNavigatesToWomenPage() {
        getPage().getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions()
                .setName("Women".toLowerCase()).setExact(true)
        ).click();

        assertThat(getPage()).hasTitle(TestData.WOMEN_TITLE);
        assertThat(getPage()).hasURL(BASE_URL + TestData.WOMEN_END_POINT);
    }

    @Test(dependsOnMethods = "TestBaseUrlLanding")
    public void testCartButtonNavigatesToCartPage(
    ) {
        getPage().getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions()
                .setName("cart")
        ).click();

        assertThat(getPage()).hasURL(BASE_URL + TestData.CART_END_POINT);
        assertThat(getPage()).hasTitle(TestData.CART_TITLE);
    }

    @Test(
            dataProvider =  "HomeLinksTestData",
            dataProviderClass = TestData.class
    )
    public void testBannersCollectionLinkNavigatesToCorrespondingPage(
            String menu, String correspondingPageTitle, String correspondingPageEndPoint
    ) {
        getPage().getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions()
                .setName(menu)
        ).click();

        assertThat(getPage()).hasTitle(correspondingPageEndPoint);
        assertThat(getPage()).hasURL(correspondingPageTitle);
    }
    @Test(dependsOnMethods = "TestBaseUrlLanding")
    public void testMenCollectionLink() {
        getPage().getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions()
                .setName("For Him").setExact(true)
        ).click();

        assertThat(getPage()).hasTitle(TestData.MEN_TITLE);
        assertThat(getPage()).hasURL(BASE_URL + TestData.MEN_END_POINT);
    }
}
