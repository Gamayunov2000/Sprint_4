package my.code.scooterTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static my.code.scooterTest.Resources.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FAQTest {

    // Создать веб-драйвер для Google Chrome
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String SCOOTER_HOME_PAGE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final By FAQ_SECTION = By.xpath(".//div[@class='accordion']");

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(SCOOTER_HOME_PAGE_URL);
        scrollToFAQSection();
    }

    @Test
    public void shouldDisplayCorrectAnswersForAllFAQQuestions() {
        HomePageScooter homePage = new HomePageScooter(driver);

        verifyFAQAnswer(homePage, 1, ONE_DAY_RENT_COST);
        verifyFAQAnswer(homePage, 2, ONE_SCOOTER_PER_ORDER);
        verifyFAQAnswer(homePage, 3, RENT_TIME_EXAMPLE);
        verifyFAQAnswer(homePage, 4, SAME_DAY_DELIVERY_UNAVAILABLE);
        verifyFAQAnswer(homePage, 5, EXTEND_RENT_CURRENTLY_NO);
        verifyFAQAnswer(homePage, 6, SCOOTER_CHARGE_INFO);
        verifyFAQAnswer(homePage, 7, CANCEL_ORDER_POLICY);
        verifyFAQAnswer(homePage, 8, DELIVERY_AREA);
    }

    private void scrollToFAQSection() {
        WebElement faqSection = driver.findElement(FAQ_SECTION);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqSection);
    }

    private void verifyFAQAnswer(HomePageScooter homePage, int questionNumber, String expectedAnswer) {
        // Кликаем по вопросу и ждем появления ответа
        homePage.expandFaqQuestion(questionNumber);
        // Ждем пока ответ станет видимым
        By answerLocator = By.id("accordion__panel-" + (questionNumber - 1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));

        // Получаем текст ответа
        String actualAnswer = homePage.getFaqAnswerText(questionNumber);

        // Проверяем соответствие
        assertThat(actualAnswer, is(expectedAnswer));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}