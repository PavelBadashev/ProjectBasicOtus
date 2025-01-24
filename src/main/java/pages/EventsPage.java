package pages;

import com.ibm.icu.text.SimpleDateFormat;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsPage extends AbsBasePage {

    private final By eventsArrayLocation = By.cssSelector("[class=\"dod_new-event-content\"]");
    private final By eventsLocation = By.cssSelector("[class=\"dod_new-events-dropdown__input-arrow\"]");
    private final By webinarItemLocation = By.cssSelector("a[href='/events/near/open_lesson/']");
    private final By eventDateLocation = By.cssSelector("[class=\"dod_new-event__date-text\"]");
    private final By eventTypeLocation = By.cssSelector("[class=\"dod_new-type__text\"]");

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    public void validateDate(String dateInput) {
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM", new Locale("ru"));
        String todateDateString = today.format(formatter);

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMMM", new Locale("ru"));

        Date inputDate = null;
        Date todayDate = null;

        try {
            inputDate = inputDateFormat.parse(dateInput);
            todayDate = inputDateFormat.parse(todateDateString);
        } catch (ParseException e) {
            logger.error("Ошибка при парсинге даты: " + e);
        }

        // Сравнение даты
        Assertions.assertTrue(inputDate != null && todayDate != null, "Ошибка при преобразовании дат");
        Assertions.assertFalse(inputDate.before(todayDate), "Даты не совпадают");
    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        long initialHeight;
        long currentHeight;

        do {
            // Запоминаем текущую высоту страницы
            initialHeight = (long) js.executeScript("return document.body.scrollHeight");

            // Скролл вниз
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Получаем новую высоту страницы
            currentHeight = (long) js.executeScript("return document.body.scrollHeight");
        } while (currentHeight > initialHeight); // Повторяем, пока высота страницы увеличивается
    }

    public void clickEventsFilter() {
        logger.info("Поиск и нажатие на меню с фильтрами мероприятий");
        driver.findElement(eventsLocation).click();

        logger.info("Нажатие на фильтр Открытый вебинар");
        driver.findElement(webinarItemLocation).click();

    }

    public void checkCardsDate(List<WebElement> cards) {
        logger.info("Валидация карточек мероприятий по дате");
        for (WebElement card : cards) {
            String dateText = String.valueOf(card.findElement(eventDateLocation).getText());
            validateDate(dateText);
        }
    }

    public void checkCardsType(List<WebElement> cards, String inputType) {
        logger.info("Валидация карточек мероприятий по типу");
        for (WebElement card : cards) {
            String eventType = String.valueOf(card.findElement(eventTypeLocation).getText());
            Assertions.assertEquals(eventType, inputType, "Карточка не прошла валидацию по типу.");
        }
    }

    public List<WebElement> getCards() {
        logger.info("Загрузка списка элементов с карточками мероприятий");
        List<WebElement> cards = driver.findElements(eventsArrayLocation);
        Assertions.assertFalse(cards.isEmpty(), "Мероприятий нет.");
        return cards;
    }


}
