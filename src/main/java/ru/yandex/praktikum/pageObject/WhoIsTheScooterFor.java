package ru.yandex.praktikum.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WhoIsTheScooterFor {
    WebDriver driver;
    private final By orderHeader = By.className("Order_Header__BZXOb");
    private final By firstName = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastName = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By address = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By stateMetro = By.className("select-search__input");
    private final By phoneNumber = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By buttonNext = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final String nameStateMetro = ".//button[@value='%s']";


    public WhoIsTheScooterFor(WebDriver driver) {
        this.driver = driver;
    }

    //метод ожидания загруки страницы заказа
    public WhoIsTheScooterFor waitForLoadOrderPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (driver.findElement(orderHeader).getText() != null
                && !driver.findElement(orderHeader).getText().isEmpty()
        ));
        return this;
    }

    public WhoIsTheScooterFor inputFirstName(String newFirstName) {
        driver.findElement(firstName).sendKeys(newFirstName);
        return this;
    }

    public WhoIsTheScooterFor inputLastName(String newLastName) {
        driver.findElement(lastName).sendKeys(newLastName);
        return this;
    }

    public WhoIsTheScooterFor inputAddress(String newAddress) {
        driver.findElement(address).sendKeys(newAddress);
        return this;
    }

    public WhoIsTheScooterFor changeStateMetro(int stateNumber) {
        driver.findElement(stateMetro).click();
        By newStateMetro = By.xpath(String.format(nameStateMetro, stateNumber));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(newStateMetro));
        driver.findElement(newStateMetro).click();
        return this;
    }

    public WhoIsTheScooterFor inputPhoneNumber(String newPhoneNumber) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(phoneNumber));
        driver.findElement(phoneNumber).sendKeys(newPhoneNumber);
        return this;
    }

    public void clickNextButton() {
        driver.findElement(buttonNext).click();
    }
}