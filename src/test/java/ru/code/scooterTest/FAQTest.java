package ru.code.scooterTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Arrays;
import java.util.Collection;
import static ru.code.scooterTest.Resources.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// ИЗМЕНЕНИЕ: Добавил Parameterized runner
@RunWith(Parameterized.class)

public class FAQTest {

    // Создать веб-драйвер для Google Chrome
    private WebDriver driver;
    private HomePageScooter homePage;
    private static final String SCOOTER_HOME_PAGE_URL = "https://qa-scooter.praktikum-services.ru";

    // ИЗМЕНЕНИЕ: Параметры для теста
    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {1, ONE_DAY_RENT_COST},
                {2, ONE_SCOOTER_PER_ORDER},
                {3, RENT_TIME_EXAMPLE},
                {4, SAME_DAY_DELIVERY_UNAVAILABLE},
                {5, EXTEND_RENT_CURRENTLY_NO},
                {6, SCOOTER_CHARGE_INFO},
                {7, CANCEL_ORDER_POLICY},
                {8, DELIVERY_AREA}
        });
    }

    @Parameterized.Parameter
    public int questionNumber;

    @Parameterized.Parameter(1)
    public String expectedAnswer;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        homePage = new HomePageScooter(driver);
        driver.get(SCOOTER_HOME_PAGE_URL);
        // ИЗМЕНЕНИЕ: Использую метод Page Object для скролла
        homePage.scrollToFAQSection();
    }

    // ИЗМЕНЕНИЕ: Заменил общий тест на параметризированный
    @Test
    public void shouldDisplayCorrectAnswerForFAQQuestion() {
        // Раскрываем вопрос и получаем ответ через методы Page Object
        homePage.expandFaqQuestion(questionNumber);
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