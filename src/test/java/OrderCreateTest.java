package ru.yandex.praktikum.pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;
import static ru.yandex.praktikum.pageObject.constants.CreateOrderButton.*;
import static ru.yandex.praktikum.pageObject.constants.RentalPeriodConstants.*;
import static ru.yandex.praktikum.pageObject.constants.ScooterColours.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private WebDriver driver;
    private final String site = "https://qa-scooter.praktikum-services.ru/";
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int stateMetroNumber;
    private final String phoneNumber;
    private final String rentalStartDate;
    private final String rentalPeriod;
    private final Enum colour;
    private final String comment;
    private final String expectedHeader = "Заказ оформлен";
    private final Enum button;

    public OrderCreateTest(Enum button, String firstName, String lastName, String address, int stateMetroNumber, String phoneNumber,
                           String rentalStartDate, String rentalPeriod, Enum colour, String comment) {
        this.button = button;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.stateMetroNumber = stateMetroNumber;
        this.phoneNumber = phoneNumber;
        this.rentalStartDate = rentalStartDate;
        this.rentalPeriod = rentalPeriod;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {UP_BUTTON, "Первый", "Фамилия", "Московская обл. ул.Первая д.1 кв.1", 5, "71111111111", "28.11.2023", ONE_DAY, GREY, "Комментарий первый"},
                {UP_BUTTON, "Второй", "Фамилия", "Адрес 2", 22, "73333333333", "01.12.2023", SEVEN_DAYS, BLACK, "Комментарий второй"},
                {DOWN_BUTTON, "Первый", "Фамилия", "Московская обл. ул.Первая д.1 кв.1", 5, "71111111111", "28.11.2023", ONE_DAY, GREY, "Комментарий первый"},
                {DOWN_BUTTON, "Второй", "Фамилия", "Адрес 2", 22, "73333333333", "01.12.2023", SEVEN_DAYS, BLACK, "Комментарий второй"},
        };
    }

    @Before
    public void startUp() {
        switch ("firefox") {
            case "firefox":
                // инициализация firefox
                WebDriverManager.firefoxdriver().clearDriverCache().clearResolutionCache().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                // инициализация chrome
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        driver.get(site);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testCreateOrderWithUpButton() {
        new HomePage(driver)
                .waitForLoadHomePage()
                .clickCreateOrderButton(button);

        new WhoIsTheScooterFor(driver)
                .waitForLoadOrderPage()
                .inputFirstName(firstName)
                .inputLastName(lastName)
                .inputAddress(address)
                .changeStateMetro(stateMetroNumber)
                .inputPhoneNumber(phoneNumber)
                .clickNextButton();

        new AboutRental(driver)
                .waitAboutRentHeader()
                .inputRentalStartDate(rentalStartDate)
                .inputRentalPeriod(rentalPeriod)
                .changeColour(colour)
                .inputComment(comment)
                .clickButtonCreateOrder();

        CreatingAnOrder creatingAnOrder = new CreatingAnOrder(driver);
        creatingAnOrder.clickButtonYes();

        assertTrue(creatingAnOrder.getHeaderAfterCreateOrder().contains(expectedHeader));
    }
}