package pages;

import com.ibm.icu.text.SimpleDateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class EventsPage extends AbsBasePage {

    private final By eventsArrayLocation = By.cssSelector("a[href^=\"https://otus.ru/lessons\"]\n");
    private final By loaderLocation = By.cssSelector("[class=\"dod_new-loader\"]");
    private final By eventsLocation = By.xpath(
            "/html/body/div[2]/div/div[1]/div/section/header/div[1]/div/div[1]/span"
    );
    private final By webinarItemLocation = By.xpath(
            "/html/body/div[2]/div/div[1]/div/section/header/div[1]/div/div[2]/a[4]"
    );
    private final By eventDateLocation = By.cssSelector("[class=\"dod_new-event__date-text\"]");
    private final By eventTypeLocation = By.cssSelector("[class=\"dod_new-type__text\"]");

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    public boolean validateDate(String dateInput) {
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM", new Locale("ru"));
        String todateDateString = today.format(formatter);

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMMM", new Locale("ru"));
        Date inputDate = null;

        try {
            inputDate = inputDateFormat.parse(dateInput);
        } catch (ParseException e) {
            logger.error(e);
        }

        Date todayDate = null;
        try {
            todayDate = inputDateFormat.parse(todateDateString);
        } catch (ParseException e) {
            logger.error(e);
        }

        // Сравниваем даты
        if (inputDate != null && todayDate != null) {
            if (inputDate.before(todayDate)) {
                logger.error("Даты не совпадают. {} <> {}", inputDate, inputDate);
                return false;
            }
        } else {
            logger.error("Ошибка при преобразовании дат.");
            return false;
        }
        return true;
    }

    public void scrollDown() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        // Инициализация JavascriptExecutor для выполнения скролла по странице
        JavascriptExecutor js = (JavascriptExecutor) driver;

        while (true) {
            // Прокрутка в самый низ страницы
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            logger.info("Поиск дополнительных событий...");
            try {
                // Ожидание появление иконки подгрузки новых событий
                wait.until(ExpectedConditions.visibilityOfElementLocated(loaderLocation));
                // Ожидание исчезновение иконки подгрузки новых событий -> события загрузились
                wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocation));
            } catch (Exception e) {
                // Если иконка не появилась за 5 секунд, значит получили все события
                logger.info("Пагинация событий прекратилась. Скролл остановился.");
                break;
            }
        }
    }

    public void clickEventsFilter(String filter) {
        logger.info("Поиск дродауна с мероприятиями");
        driver.findElement(eventsLocation).click();

        logger.info("Поиск и выбор элемента - {}", filter);
        driver.findElement(webinarItemLocation).click();

    }

    public boolean checkCardsDate(List<WebElement> cards) {
        logger.info("Валидация карточек мероприятий по дате");
        for (WebElement card : cards) {
            String dateText = String.valueOf(card.findElement(eventDateLocation).getText());
            if (!validateDate(dateText)) {
                logger.error("Карточка не прошла валидацию по дате: {}", dateText);
                return false;
            }
        }
        return true;
    }

    public boolean checkCardsType(List<WebElement> cards, String inputType) {
        logger.info("Валидация карточек мероприятий по типу");
        for (WebElement card : cards) {
            String eventType = String.valueOf(card.findElement(eventTypeLocation).getText());
            if (!Objects.equals(eventType, inputType)) {
                logger.error("Карточка не прошла валидацию по типу: {}", inputType);
                return false;
            }
        }
        return true;
    }

    public List<WebElement> getCards() {
        logger.info("Загрузка списка элементов с карточками мероприятий");
        return driver.findElements(eventsArrayLocation);
    }


}
