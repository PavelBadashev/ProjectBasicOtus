import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CardPage;
import pages.EventsPage;
import pages.MainPage;
import pages.TestCoursesPage;

import java.util.List;

public class MainTest {
    WebDriver driver;
    private static final Logger logger = LogManager.getLogger(MainTest.class);

    // Перед каждым шагом сетапится драйвер
    @BeforeEach
    void beforeEach() {
        driver = new DriverFactory().getDriver();
    }

    // После каждого шага закрывается драйвер
    @AfterEach
    void afterEach(){
        if (driver != null) {
            driver.quit();
        }
    }

    @DisplayName("Проверка количества курсов в разделе тестирование")
    @ParameterizedTest
    @ValueSource(ints = {11})
    void checkTestCoursesAmount(Integer expectedAmount){
        /*
            Тест упадет на последней (11) карточке - ручное тестирование т.к. её структура отличается от первых 10.
        */
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickTrainingMenu().clickTestingSection();

        TestCoursesPage testPage = new TestCoursesPage(driver);
        List<WebElement> testingCourses = testPage.getTestingCoursesInfo();
        Assertions.assertEquals(
                testingCourses.size(),
                expectedAmount,
                "Количество курсов на странице не соответствует ожидаемому значению!"
        );

        for (int cardIndex = 0; cardIndex < testingCourses.size(); cardIndex++) {
            WebElement card = testPage.getTestingCoursesInfo(cardIndex);
            CardPage cardPage = new CardPage(driver);
            testPage.openCard(card);
            Assertions.assertTrue(cardPage.checkCardInfo());
        }
    }

    @DisplayName("Проверка типов и дат мероприятий")
    @Test
    void testEventsCheck(){
        String webinarType = "Открытый вебинар";

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickTrainingMenu().clickCalendarSection();

        EventsPage eventsPage = new EventsPage(driver);
        eventsPage.clickEventsFilter(webinarType);
        eventsPage.scrollDown();
        List<WebElement> cards = eventsPage.getCards();
        Assertions.assertTrue(
                eventsPage.checkCardsDate(cards),
                "Тест не пройден на этапе валидации дат карточек с мероприятиями."
        );
        Assertions.assertTrue(
                eventsPage.checkCardsType(cards, webinarType),
                "Тест не пройден на этапе валидации фильтрации карточек по типу."
        );
    }
}
