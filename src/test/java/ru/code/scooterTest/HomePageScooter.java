package ru.code.scooterTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePageScooter {
    private final WebDriver driver;
    // ИЗМЕНЕНИЕ: Перенес локатор секции FAQ в Page Object и сделал private
    private final By FAQ_SECTION = By.xpath(".//div[@class='accordion']");

    // ИЗМЕНЕНИЕ: Убрал модификатор static, сделал поля приватными
    // FAQ Section Locators
    private final By[] FAQ_QUESTIONS = {
            // Сколько это стоит? И как оплатить?
            By.xpath(".//div[@class='accordion__item'][1]"),
            // Хочу сразу несколько самокатов! Так можно?
            By.xpath(".//div[@class='accordion__item'][2]"),
            // Как рассчитывается время аренды?
            By.xpath(".//div[@class='accordion__item'][3]"),
            // Можно ли заказать самокат прямо на сегодня?
            By.xpath(".//div[@class='accordion__item'][4]"),
            // Можно ли продлить заказ или вернуть самокат раньше?
            By.xpath(".//div[@class='accordion__item'][5]"),
            // Вы привозите зарядку вместе с самокатом?
            By.xpath(".//div[@class='accordion__item'][6]"),
            // Можно ли отменить заказ?
            By.xpath(".//div[@class='accordion__item'][7]"),
            //Я живу за МКАДом, привезёте?
            By.xpath(".//div[@class='accordion__item'][8]")
    };

    // ИЗМЕНЕНИЕ: Убрал модификатор static, сделал поля приватными
    private final By[] FAQ_ANSWERS = {
            // Сутки — 400 рублей. Оплата курьеру — наличными или картой.
            By.id("accordion__panel-0"),
            // Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями,
            // можете просто сделать несколько заказов — один за другим.
            By.id("accordion__panel-1"),
            // Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня.
            // Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру.
            // Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.
            By.id("accordion__panel-2"),
            // Только начиная с завтрашнего дня. Но скоро станем расторопнее.
            By.id("accordion__panel-3"),
            // Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.
            By.id("accordion__panel-4"),
            // Самокат приезжает к вам с полной зарядкой.
            // Этого хватает на восемь суток — даже если будете кататься без передышек и во сне.
            // Зарядка не понадобится.
            By.id("accordion__panel-5"),
            // Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим.
            // Все же свои.
            By.id("accordion__panel-6"),
            // Да, обязательно. Всем самокатов! И Москве, и Московской области.
            By.id("accordion__panel-7")
    };

    // Маленькая кнопка Заказать в шапке
    private final By headerOrderButton = By.xpath(".//button[text()='Заказать'][1]");

    // Большая кнопка Заказать на странице
    private final By mainOrderButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]/button");

    public HomePageScooter(WebDriver driver) {
        this.driver = driver;
    }

    // ИЗМЕНЕНИЕ: Добавил метод для скролла к секции FAQ
    public void scrollToFAQSection() {
        WebElement faqSection = driver.findElement(FAQ_SECTION);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqSection);
    }

    // Метод для раскрытия вопросов
    public void expandFaqQuestion(int questionNumber) {
        if (questionNumber < 1 || questionNumber > FAQ_QUESTIONS.length) {
            throw new IllegalArgumentException("Неверный номер вопроса");
        }
        //driver.findElement(FAQ_QUESTIONS[questionNumber - 1]).click();

        // ИЗМЕНЕНИЕ: Добавил явное ожидание для кликабельности элемента
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement question = wait.until(
                ExpectedConditions.elementToBeClickable(FAQ_QUESTIONS[questionNumber - 1])
        );
        question.click();

        // ИЗМЕНЕНИЕ: Добавил ожидание появления ответа
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(FAQ_ANSWERS[questionNumber - 1])
        );
    }

    public String getFaqAnswerText(int answerNumber) {
        if (answerNumber < 1 || answerNumber > FAQ_ANSWERS.length) {
            throw new IllegalArgumentException("Неверный номер ответа");
        }
        return driver.findElement(FAQ_ANSWERS[answerNumber - 1]).getText();
    }

    // Кликаем по кнопке Заказать
    public void clickHeaderOrderButton() {
        driver.findElement(headerOrderButton).click();
    }

    public void clickMainOrderButton() {
        // Переходим вниз до появления кнопки
        WebElement button = driver.findElement(mainOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        button.click();
    }
}