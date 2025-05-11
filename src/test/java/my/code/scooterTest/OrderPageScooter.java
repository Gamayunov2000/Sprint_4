package my.code.scooterTest;

import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;

public class OrderPageScooter {
    private final WebDriver driver;

    // Locators
    private final By orderFormTitle = By.xpath(".//div[text()='Для кого самокат']");
    private final By rentalDetailsTitle = By.xpath(".//div[text()='Про аренду']");
    private final By acceptCookiesButton = By.xpath(".//button[text()='да все привыкли']");

    // Customer info fields
    private final By firstNameInput = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastNameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationInput = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Rental details fields
    private final By deliveryDateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.xpath(".//div[@class='Dropdown-placeholder']");
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By createOrderButton = By.xpath("//div[contains(@class,'Order_Buttons')]/button[text()='Заказать']");

    // Order confirmation
    private final By confirmOrderButton = By.xpath(".//button[text()='Да']");
    private final By orderStatusButton = By.xpath(".//button[text()='Посмотреть статус']");

    public OrderPageScooter(WebDriver driver) {
        this.driver = driver;
    }

    //Получаем текст заголовка страницы заказа
    public String getOrderFormTitle() {
        return driver.findElement(orderFormTitle).getText();
    }

    //Получаем текста на кнопке для просмотра статуса заказа
    public String getOrderStatusButtonText() { //getConfirmHeader
        return driver.findElement(orderStatusButton).getText();
    }

    //Для проверки открытия страницы
    public void verifyPageHeader(String actualHeader, String expectedHeader) {
        MatcherAssert.assertThat(actualHeader, is(expectedHeader));
    }

    //Для принятия куки
    public void acceptCookies() {
        driver.findElement(acceptCookiesButton).click();
    }

    //Для заполнения поля Имя
    public void enterFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    //Для заполнения поля Фамилия
    public void enterLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    //Для заполнения поля Адрес: куда привезти заказ
    public void enterAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    //Для заполнения поля Станция метро
    public void selectMetroStation(String station) {
        driver.findElement(metroStationInput).click();
        driver.findElement(By.xpath(".//div[text()='" + station + "']")).click();
    }

    //Для заполнения поля Телефон: на него позвонит курьер
    public void enterPhoneNumber(String phone) { //clickOrderNextButton
        driver.findElement(phoneInput).sendKeys(phone);
    }

    //Для перехода ко второй странице создания заказа
    public void proceedToRentalDetails() {
        driver.findElement(nextButton).click();
    }

    //Для заполнения поля Когда привезти самокат
    public void setDeliveryDate(String date) {
        driver.findElement(deliveryDateInput).sendKeys(date);
    }

    //Для заполнения поля Срок аренды
    public void selectRentalPeriod(String period) {
        driver.findElement(rentalDetailsTitle).click(); // Ensure focus
        driver.findElement(rentalPeriodDropdown).click();
        driver.findElement(By.xpath(".//div[text()='" + period + "']")).click();
    }

    //Для заполнения поля Цвет самоката
    public void selectScooterColor(String color) {
        driver.findElement(By.xpath(".//label[text()='" + color + "']")).click();
    }

    //Для заполнения поля Комментарий для курьера
    public void addComment(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    //Для перехода к подтверждению заказа
    public void submitOrder() {
        driver.findElement(createOrderButton).click();
    }

    //Для подтверждения заказа
    public void confirmOrder() {
        driver.findElement(confirmOrderButton).click();
    }
}