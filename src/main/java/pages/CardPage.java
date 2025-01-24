package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class CardPage extends AbsBasePage {

    // Наименование курса
//    private final By nameLocation = By.cssSelector("[class=\"sc-1x9oq14-0 sc-s2pydo-1 kswXpy diGrSa\"]");
    private final By nameLocation = By.xpath("//h1");
    // Описание
    private final By descriptionLocation = By.cssSelector("[class=\"sc-1x9oq14-0 sc-s2pydo-3 enpOeQ dZDxRw\"]");
    // Краткое описание курса (Дата, уровень, длительность, формат, расписание)
    private final By shortInfoLocation = By.cssSelector("[class=\"sc-1x9oq14-0 sc-3cb1l3-0 doSDez dgWykw\"]");

    WebDriverWait wait = new WebDriverWait(driver, 10);

    public CardPage(WebDriver driver) {
        super(driver);
    }

    public void checkCardName() {
        String name = driver.findElement(nameLocation).getText();
        logger.info(name);
        Assertions.assertFalse(
                name == null || name.isEmpty(),
                "Наименование отсутствует на странице карточки!"
        );
    }

    public void checkCardDescription() {
        String description = driver.findElement(descriptionLocation).getText();
        Assertions.assertFalse(
                description == null || description.isEmpty(),
                "Описание отсутствует на странице карточки!"
        );
    }

    public void checkCardDuration(List<WebElement> elementsList) {
        String duration = elementsList.get(2).getText();

        Assertions.assertFalse(
                duration == null || duration.isEmpty(),
                "Длительность курса отсутствует на странице карточки!"
        );
    }

    public void checkCardFormat(List<WebElement> elementsList) {
        String courseFormat = elementsList.get(3).getText();

        Assertions.assertFalse(
                courseFormat == null || courseFormat.isEmpty(),
                "Формат курса отсутствует на странице карточки!"
        );
    }

    public void checkCardInfo() {
        // Получение массива элементов с краткой инфорацией по курсу
        List<WebElement> generalInfo = driver.findElements(shortInfoLocation);
        wait.until(ExpectedConditions.visibilityOfAllElements(generalInfo));

        logger.info("Поиск наименования курса");
        checkCardName();

        logger.info("Поиск описания курса");
        checkCardDescription();

        logger.info("Поиск длительности курса");
        checkCardDuration(generalInfo);

        logger.info("Поиск формата курса");
        checkCardFormat(generalInfo);
    }
}
