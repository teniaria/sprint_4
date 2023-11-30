package ru.yandex.praktikum.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.pageObject.constants.RentalPeriodConstants;

import java.time.Duration;

import static ru.yandex.praktikum.pageObject.constants.ScooterColours.*;

public class AboutRental {
    WebDriver driver;
    private final By rentHeader = By.className("Order_Header__BZXOb");
    private final By rentalStartDate = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.xpath(".//span[@class='Dropdown-arrow']");
    private final By rentalPeriodOne = By.xpath(".//div[@class='Dropdown-option' and text()='сутки']");
    private final By rentalPeriodSeven = By.xpath(".//div[@class='Dropdown-option' and text()='семеро суток']");
    private final By colourBlack = By.id("black");
    private final By colourGrey = By.id("grey");
    private final By comment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By createOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");


    public AboutRental(WebDriver driver) {
        this.driver = driver;
    }

    //метод ожидания загрузки страницы
    public AboutRental waitAboutRentHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (driver.findElement(rentHeader).getText() != null
                && !driver.findElement(rentHeader).getText().isEmpty()
        ));
        return this;
    }

    public AboutRental inputRentalStartDate(String newRentalStartDate) {
        driver.findElement(rentalStartDate).sendKeys(newRentalStartDate);
        return this;
    }

    public AboutRental inputRentalPeriod(String newRentalPeriod) {
        driver.findElement(rentalPeriod).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(By.className("Dropdown-menu"))).getText();

        if (newRentalPeriod.equals(RentalPeriodConstants.SEVEN_DAYS)) {
            driver.findElement(rentalPeriodSeven).click();
        } else if (newRentalPeriod.equals(RentalPeriodConstants.ONE_DAY)) {
            driver.findElement(rentalPeriodOne).click();
        }
        return this;
    }

    public AboutRental changeColour(Enum colour) {
        if (colour.equals(BLACK)) {
            driver.findElement(colourBlack).click();
        } else if (colour.equals(GREY)) {
            driver.findElement(colourGrey).click();
        }
        return this;
    }

    public AboutRental inputComment(String newComment) {
        driver.findElement(comment).sendKeys(newComment);
        return this;
    }

    public void clickButtonCreateOrder() {
        driver.findElement(createOrderButton).click();
    }
}
