package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class CardPage extends AbsBasePage {

    // Наименование курса
    private final By nameLocation = By.cssSelector("[class=\"sc-1x9oq14-0 sc-s2pydo-1 kswXpy diGrSa\"]");
    // Описание
    private final By descriptionLocation = By.cssSelector("[class=\"sc-1x9oq14-0 sc-s2pydo-3 enpOeQ dZDxRw\"]");
    // Краткое описание курса (Дата, уровень, длительность, формат, расписание)
    private final By shortInfoLocation = By.cssSelector("[class=\"sc-1x9oq14-0 sc-3cb1l3-0 doSDez dgWykw\"]");


    public CardPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkCardInfo() {
        logger.info("Поиск наименования курса");
        String name = driver.findElement(nameLocation).getText();
        if (name == null || name.isEmpty()) {
            logger.error("Наименование отсутствует на странице карточки!");
            return false;
        }
        logger.info("Найдено наименование карточки: {}", name);

        logger.info("Поиск описания курса");
        String description = driver.findElement(descriptionLocation).getText();
        if (description == null || description.isEmpty()) {
            logger.error("Описание отсутствует на странице карточки!");
            return false;
        }

        // Получение массива элементов с краткой инфорацией по курсе
        List<WebElement> generalInfo = driver.findElements(shortInfoLocation);

        // Получение строки с информацией о длительности курса
        logger.info("Поиск длительности курса");
        String duration = generalInfo.get(2).getText();
        if (duration == null || duration.isEmpty()) {
            logger.error("Длительность курса отсутствует на странице карточки!");
            return false;
        }

        // Получение строки с информацией о формате проведения занятий
        logger.info("Поиск формата курса");
        String courseFormat = generalInfo.get(3).getText();
        if (courseFormat == null || courseFormat.isEmpty()) {
            logger.error("Формат курса отсутствует на странице карточки!");
            return false;
        }

        // Возврат на страницу с карточками
        driver.navigate().back();

        return true;
    }
}
