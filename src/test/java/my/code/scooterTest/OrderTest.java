package my.code.scooterTest;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

import static my.code.scooterTest.Resources.ORDER_STATUS_HEADER;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private final String firstName;
    private final String lastName;
    private final String deliveryAddress;
    private final String metroStation;
    private final String phone;
    private final String deliveryDate;
    private final String rentalDuration;
    private final String scooterColor;
    private final String deliveryComment;

    public OrderTest(String firstName, String lastName, String deliveryAddress,
                     String metroStation, String phone, String deliveryDate,
                     String rentalDuration, String scooterColor, String deliveryComment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalDuration = rentalDuration;
        this.scooterColor = scooterColor;
        this.deliveryComment = deliveryComment;
    }

    @Parameterized.Parameters(name = "Test data: {0} {1}")
    public static Object[][] getOrderTestData() {
        return new Object[][] {
                {"Алексей", "Смирнов", "ул. Ленина, д. 42, кв. 15", "Черкизовская",
                        "89991234567", "15.05.2025", "трое суток", "чёрный жемчуг", "Оставить у подъезда"},
                {"Мария", "Кузнецова", "пр. Науки, д. 7", "Академическая",
                        "+79031234567", "20.06.2025", "пятеро суток", "серая безысходность", "Позвонить за час"},
        };
    }

    @Test
    public void successfulOrderCreationTest() {
        // Инициализация драйвера и открытие страницы
        //driver = new FirefoxDriver();
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru");

        // Работа с главной страницей
        HomePageScooter homePage = new HomePageScooter(driver);
        homePage.clickHeaderOrderButton();

        //Работа со страницей оформления заказа
        OrderPageScooter orderPage = new OrderPageScooter(driver);
        orderPage.acceptCookies();

        // Позитивный сценарий оформления заказа
        orderPage.enterFirstName(firstName);
        orderPage.enterLastName(lastName);
        orderPage.enterAddress(deliveryAddress);
        orderPage.selectMetroStation(metroStation);
        orderPage.enterPhoneNumber(phone);
        orderPage.proceedToRentalDetails();

        // Заполнение данных аренды
        orderPage.setDeliveryDate(deliveryDate);
        orderPage.selectRentalPeriod(rentalDuration);
        orderPage.selectScooterColor(scooterColor);
        orderPage.addComment(deliveryComment);

        // Подтверждение заказа
        orderPage.submitOrder();
        orderPage.confirmOrder();

        // Проверка успешного оформления
        orderPage.verifyPageHeader(orderPage.getOrderStatusButtonText(), ORDER_STATUS_HEADER);
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}