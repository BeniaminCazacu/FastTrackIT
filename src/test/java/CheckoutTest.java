import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

// This annotation is used to specify that the ExtentTestNGITestListener class should be used to listen to the test events.
// The listener will generate reports based on the test results using the ExtentReports library.
@Listeners(ExtentTestNGITestListener.class)

// Declaring the CheckoutTest class, which extends the Hooks class.
// By extending Hooks, CheckoutTest inherits the setup and teardown methods for WebDriver.
public class CheckoutTest extends Hooks {

    // Declaring a public variable of type CheckoutPage named 'checkoutPage'.
    // This will be used to interact with the CheckoutPage object during the tests.
    public CheckoutPage checkoutPage;

    // Declaring a public variable of type WebDriverWait named 'wait'.
    // WebDriverWait is used to explicitly wait for certain conditions or elements during test execution.
    public WebDriverWait wait;

    // Method annotated with @BeforeMethod, indicating that it will run before each test method.
    // This method is used to set up the page objects and other necessary components before each test.
    @BeforeMethod
    public void SetupPageObject() {

        // Initializing the checkoutPage object with the current WebDriver instance.
        // This allows the test methods to interact with elements on the checkout page.
        checkoutPage = new CheckoutPage(driver);

        // Initializing the WebDriverWait object with the current WebDriver instance and a timeout of 30 seconds.
        // This wait will be used to pause the execution until certain conditions are met or elements are found.
        wait = new WebDriverWait(driver, 30);
    }


    @Test(description = "Tests the search functionality by searching for the keyword 'mouse'")
    public void searchTest() {
        checkoutPage.setSearchBar();
        checkoutPage.clickSearchButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The search engine is looking up for the keyword 'mouse'");
    }

    @Test(description = "Purchasing a simple product from a guest user")
    public void checkoutTest() {
        checkoutPage.clickAwesomeChipsLink();
        checkoutPage.clickCartIcon();
        checkoutPage.clickShoppingCartIcon();
        checkoutPage.clickCheckoutButton();
        checkoutPage.insertFirstName();
        checkoutPage.insertLastName();
        checkoutPage.insertAddress();
        checkoutPage.clickContinueCheckout();
        checkoutPage.clickCompleteOrder();
        assertEquals(checkoutPage.getSuccessMessage().getText(), "Order complete");
    }

    @Test(description = "Add element to wishlist")
    public void wishlistTest(){
        checkoutPage.clickAwesomeChipsLink();
        checkoutPage.clickHeartIcon();
        assertEquals(checkoutPage.getShoppingCartBadge().getText(), "1");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "Shopping cart badge was updated with success.");
        checkoutPage.clickShoppingCartBadge();
        assertEquals(checkoutPage.getAwesomeChipsProduct().getText(), "Awesome Granite Chips");
        checkoutPage.clickBrokenHeartIcon();
        ExtentTestNGITestListener.getTest().log(Status.PASS, "Awesome granite chips product was found in the wishlist.");
        //assertNotEquals(checkoutPage.getAwesomeChipsProduct().getText(), "Awesome Granite Chips");
    }

    @Test(description = "Increase the amount of a product")
    public void increasedAmountTest() {
        checkoutPage.clickAwesomeShirt();
        checkoutPage.clickAdd2Cart();
        checkoutPage.clickCartBtn();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is: " + checkoutPage.productPrice());
        double expectedTotal = checkoutPage.productPrice() * 2;
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product after quantity increase should be: " + expectedTotal);
        checkoutPage.clickPlusOne();
        assertEquals(checkoutPage.productPrice(), expectedTotal);
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product matches expected total " + checkoutPage.productPrice() + " = " + expectedTotal);
    }

    @Test(description = "Compare two smartphones on www.emag.ro")
    public void compareTwoSmartphones() throws InterruptedException {
        checkoutPage.insertTextInSearchBox("google pixel 9 pro");
        Thread.sleep(1000);
        checkoutPage.clickMainSearchButton();
        Thread.sleep(1000);
        checkoutPage.clickFirstSmartphone();
        Thread.sleep(1000);
        checkoutPage.clickCompareCheckbox();
        Thread.sleep(1000);
        ExtentTestNGITestListener.getTest().log(Status.INFO, "First smartphone is added to comparison");
        checkoutPage.insertTextInSearchBox("samsung galaxy s24 ultra");
        Thread.sleep(1000);
        checkoutPage.clickMainSearchButton();
        Thread.sleep(10000);//page takes longer to load
        checkoutPage.clickSecondSmartphone();
        Thread.sleep(1000);
        checkoutPage.clickCompareCheckbox();
        Thread.sleep(1000);
        ExtentTestNGITestListener.getTest().log(Status.INFO, "Second smartphone is added to comparison");
        checkoutPage.clickCompareButton();
        Thread.sleep(1000);
    }

    @Test(description = "Add/Delete product to/from favorite on emag")
    public void AddToFavorite() throws InterruptedException {
        checkoutPage.insertTextInSearchBox("google pixel 9 pro");
        Thread.sleep(1000);
        checkoutPage.clickMainSearchButton();
        Thread.sleep(10000);
        checkoutPage.clickAddToFavoriteButton();
        Thread.sleep(1000);
        checkoutPage.clickWishlist();
        Thread.sleep(1000);
        assertEquals(checkoutPage.favoriteProducts(), "1");
        checkoutPage.clickDeleteProductButton();
        Thread.sleep(1000);
        assertEquals(checkoutPage.favoriteProducts(), "0");
    }

    @Test(description = "Apply some filters for laptop category on emag")
    public void applyFilters() throws InterruptedException {
        checkoutPage.clickMenu();
        Thread.sleep(1000);
        checkoutPage.clickLaptopTabletPhoneCategory();
        Thread.sleep(1000);
        checkoutPage.clickLaptopsAndAccessories();
        Thread.sleep(1000);
        checkoutPage.clickLaptops();
        Thread.sleep(1000);
        checkoutPage.insertMinimPrice();
        Thread.sleep(1000);
        checkoutPage.insertMaximPrice();
        Thread.sleep(1000);
        checkoutPage.clickIntervalFilterButton();
        Thread.sleep(5000);
        checkoutPage.clickMemoryRAM();
        Thread.sleep(5000);
    }
}

