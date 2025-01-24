import driver.DriverFactory;
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

    // Перед каждым шагом сетапится драйвер
    @BeforeEach
    void beforeEach() {
        driver = new DriverFactory().getDriver();
    }

    // После каждого шага закрывается драйвер
    @AfterEach
    void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DisplayName("Проверка количества курсов в разделе тестирование")
    @ParameterizedTest
    @ValueSource(ints = {11})
    void checkTestCoursesAmount(Integer expectedAmount) {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickTrainingMenu().clickTestingSection();

        TestCoursesPage testPage = new TestCoursesPage(driver);
        List<WebElement> testingCourses = testPage.getCards();
        Assertions.assertEquals(
                expectedAmount,
                testingCourses.size(),
                "Количество курсов на странице не соответствует ожидаемому значению!"
        );

    }

    @DisplayName("Проверка основных полей в карточке")
    @Test
    void testCardFields() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickTrainingMenu().clickTestingSection();

        TestCoursesPage testPage = new TestCoursesPage(driver);
        WebElement card = testPage.getCards().getFirst();
        CardPage cardPage = new CardPage(driver);
        testPage.openCard(card);
        cardPage.checkCardInfo();
    }

    @DisplayName("Проверка типов и дат мероприятий")
    @Test
    void testEventsCheck() {
        String webinarType = "Открытый вебинар";

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickTrainingMenu().clickCalendarSection();

        EventsPage eventsPage = new EventsPage(driver);
        eventsPage.clickEventsFilter();
        eventsPage.scrollDown();

        List<WebElement> cards = eventsPage.getCards();
        eventsPage.checkCardsDate(cards);
        eventsPage.checkCardsType(cards, webinarType);
    }
}
