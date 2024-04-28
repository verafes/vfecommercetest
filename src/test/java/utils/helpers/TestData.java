package utils.helpers;

import org.testng.annotations.DataProvider;

public class TestData {
    /* end points */
    public static final String BASE_URL = "http://localhost:3000";
    public static final String HOME_END_POINT = "/";
    public static final String WOMEN_END_POINT = "/women";
    public static final String MEN_END_POINT = "/men";
    public static final String SHOES_END_POINT = "/shoes";
    public static final String ACCESSORIES_END_POINT = "/accessories";
    public static final String SEARCH_END_POINT = "/search";
    public static final String CART_END_POINT = "/cart";
    public static final String ABOUT_END_POINT = BASE_URL + HOME_END_POINT + "#footer-about";

    /* Data > titles */
    public static final String BASE_TITLE = "VF | Elegant Clothing For Her and For Him";
    public static final String WOMEN_TITLE = "VF Shop | For Her";
    public static final String MEN_TITLE = "VF Shop | For Him";
    public static final String SHOES_TITLE = "VF Shop | Shoes";
    public static final String ACCESSORIES_TITLE = "VF Shop | Accessories";
    public static final String SEARCH_TITLE = "VF Shop | Search Results";
    public static final String CART_TITLE = "VF Shop | Cart";
    public static final String ABOUT_HEADING = "ABOUT COMPANY:";
    public static final String SEARCH_MENU = "Search";


    @DataProvider(name="NavigationBarTestData")
    public static Object[][] getMenuTestDataProvider() {

        return new Object[][]{
                {"Home", BASE_TITLE, BASE_URL + HOME_END_POINT},
                {"Women", WOMEN_TITLE, BASE_URL + WOMEN_END_POINT },
                {"Men", MEN_TITLE, BASE_URL + MEN_END_POINT},
                {"Shoes",  SHOES_TITLE, BASE_URL + SHOES_END_POINT},
                {"Accessories", ACCESSORIES_TITLE, BASE_URL + ACCESSORIES_END_POINT},
                {"About", BASE_TITLE, ABOUT_END_POINT},
        };
    }

    @DataProvider(name="HomeLinksTestData")
    public static Object[][] getHomeLinksTestDataProvider() {

        return new Object[][]{
                {"For Her", WOMEN_TITLE, BASE_URL + WOMEN_END_POINT },
                {"For Him", MEN_TITLE, BASE_URL + MEN_END_POINT},
                {"accessories collection", ACCESSORIES_TITLE, BASE_URL + ACCESSORIES_END_POINT},
                {"Cart", CART_TITLE, BASE_URL + CART_END_POINT},
                {"Wishlist", CART_TITLE, BASE_URL + CART_END_POINT}
        };
    }
}
