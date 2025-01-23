package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TestCoursesPage extends AbsBasePage{
    // Локатор кнопки "Показать еще"
    private final By extendButton = By.cssSelector("[class=\"sc-mrx253-0 enxKCy sc-prqxfo-0 cXVWAS\"]");
    // Локатор класса с элементами курсов
    private final By elementsClass = By.cssSelector("[class=\"sc-18q05a6-1 bwGwUO\"]");
    // Локатор элементов курсов
    private final By innerCourses = By.cssSelector("a[href^='/']");
    // Локатор карточки курса

    public TestCoursesPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getTestingCoursesInfo() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        logger.info("Раскрытие курсов скрытых пагинацией");
        driver.findElement(extendButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(extendButton));

        logger.info("Поиск и подсчет карточек курсов тестирования");
        WebElement elements = driver.findElement(elementsClass);

        // TODO Не удалось пока зацепиться за иконку обновления списка карточек внизу страницы
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
            logger.error(e);
        }

        // Поиск дочерних элементов (карточек) внутри класса
        return elements.findElements(innerCourses);
    }

    public WebElement getTestingCoursesInfo(Integer cardNumber) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        logger.info("Раскрытие курсов скрытых пагинацией (если такие имеются)");
        try {
            driver.findElement(extendButton).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(extendButton));
        } catch (Exception e) {
            logger.info("Кнопки 'Показать ещё' нет на экране, в раскрытии нет необходимости.");
        }

        logger.info("Поиск и подсчет карточек курсов тестирования");
        // Поиск класса с карточками
        WebElement elements = driver.findElement(elementsClass);

        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
            logger.error(e);
        }

        // Поиск дочерних элементов (карточек) внутри класса
        return elements.findElements(innerCourses).get(cardNumber);
    }

    public void openCard(WebElement card) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        logger.info("Переход на страницу с информацией по картчоке");
        card.click();
    }
}
