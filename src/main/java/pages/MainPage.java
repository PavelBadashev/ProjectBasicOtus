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
    private final By buttonTestingLocation = By.cssSelector("a[href*='/categories/testing']");

    // Локатор события "Календарь мероприятий" в меню "Обучение"
    private final By buttonCalendarLocation = By.cssSelector("[href=\"https://otus.ru/events/near\"]");

    Actions actions = new Actions(driver);
    WebDriverWait wait = new WebDriverWait(driver, 10);

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage clickTrainingMenu() {
        logger.info("Поиск меню 'Обучение'");
        WebElement studingElement = driver.findElement(buttonStudingLocation);

        logger.info("Наведение курсора на 'Обучение'");
        actions.moveToElement(studingElement).perform();

        return this;
    }

    public void clickTestingSection(){
        logger.info("Нажатие на раздел 'Тестирование'");
        driver.findElement(buttonTestingLocation).click();
    }

    public void clickCalendarSection(){
        logger.info("Нажатие на раздел 'Календарь мероприятий'");
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonCalendarLocation));
        driver.findElement(buttonCalendarLocation).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(buttonCalendarLocation));
    }
}
