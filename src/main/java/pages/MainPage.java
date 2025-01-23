package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends AbsBasePage{

    // Локатор меню "Обучение"
    private final By buttonStudingLocation = By.cssSelector("span[title='Обучение']");

    // Локатор курса "Тестирование" в меню "Обучение"
    private final By buttonTestingLocation = By.cssSelector(
            "a.sc-1pgqitk-0.dNitgt[href=\"https://otus.ru/categories/testing\"]"
    );

    // Локатор события "Календарь мероприятий" в меню "Обучение"
    private final By buttonCalendarLocation = By.cssSelector(
            "a.sc-1pgqitk-0.dNitgt[href=\"https://otus.ru/events/near\"]"
    );

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage clickTrainingMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Создание объекта Actions для совершения действий на странице
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonStudingLocation));

        logger.info("Поиск меню 'Обучение'");
        WebElement studingElement = driver.findElement(buttonStudingLocation);

        logger.info("Наведение курсора на 'Обучение'");
        action.moveToElement(studingElement).perform();
        return this;
    }

    public void clickTestingSection(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        logger.info("Нажатие на раздел 'Тестирование'");
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonTestingLocation));
        driver.findElement(buttonTestingLocation).click();
    }

    public void clickCalendarSection(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        logger.info("Нажатие на раздел 'Календарь событий'");
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonCalendarLocation));
        driver.findElement(buttonCalendarLocation).click();
    }
}
