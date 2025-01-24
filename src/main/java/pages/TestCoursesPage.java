package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TestCoursesPage extends AbsBasePage {
    // Локатор кнопки "Показать еще"
    private final By extendButton = By.xpath("//button[text()='Показать еще 1']");
    // Локатор класса с элементами курсов
    private final By elementsClass = By.cssSelector("[class=\"sc-18q05a6-1 bwGwUO\"]"); // не смог найти альтернативу
    // Локатор элементов курсов
    private final By innerCourses = By.cssSelector("a[href^='/']");

    Actions actions = new Actions(driver);
    WebDriverWait wait = new WebDriverWait(driver, 2);

    public TestCoursesPage(WebDriver driver) {
        super(driver);
    }

    public void clickExtend() {
        logger.info("Раскрытие курсов скрытых пагинацией");
        try {
            WebElement button = driver.findElement(extendButton);
            button.click();
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(extendButton));
            } catch (Exception e) {
                logger.info("Кнопки 'Показать ещё' нет на экране");
            }
        } catch (Exception e) {
            logger.info("Ошибка при нажатии кнопки \"Показать еще\"");
        }
    }

    public List<WebElement> getCards() {
        clickExtend();

        logger.info("Поиск и подсчет карточек курсов тестирования");
        WebElement elements = driver.findElement(elementsClass);

        // Поиск дочерних элементов (карточек) внутри класса
        return elements.findElements(innerCourses);
    }

    public void openCard(WebElement card) {
        logger.info("Переход на страницу с информацией по карточке");

        // Используйте Actions для установки фокуса
        actions.moveToElement(card).perform(); // Перемещение к элементу
        actions.click().perform(); // Клик на элементе
//        wait.until(ExpectedConditions.visibilityOf(card));
    }
}
