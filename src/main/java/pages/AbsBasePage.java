package pages;

import config.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class AbsBasePage {
    protected WebDriver driver;
    public static final Logger logger = LogManager.getLogger(AbsBasePage.class);

    public AbsBasePage(WebDriver driver){
        this.driver = driver;
    }

    public void open(){
            driver.get(Utils.getUrl());
    }
}
