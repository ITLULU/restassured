package utils;

import io.appium.java_client.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AppiumUtils {

    // 滑动操作 向上滑动
    public void swipeToUp(AppiumDriver<WebElement> driver, int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int startx = width / 4;
        int starty = 3 * height / 4;
        int endx = startx;
        int endy = height / 4;
        @SuppressWarnings("rawtypes")
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startx, starty).waitAction(Duration.ofSeconds(during)).moveTo(endx, endy).release().perform();
    }

    // 向下滑动
    public void swipeToDown(AppiumDriver<WebElement> driver, int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int startx = width / 4;
        int starty = height / 4;
        int endx = startx;
        int endy = 3 * height / 4;
        @SuppressWarnings("rawtypes")
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startx, starty).moveTo(endx, endy).release().perform();
    }

    // 向右滑
    public void swipeToRight(AppiumDriver<WebElement> driver, int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int startx = width / 4;
        int starty = height / 3;
        int endx = 3 * width / 4;
        int endy = height / 3;
        @SuppressWarnings("rawtypes")
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startx, starty).moveTo(endx, endy).release().perform();
    }

    // 向左滑
    public void swipeToLeft(AppiumDriver<WebElement> driver, int during) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int startx = 3 * width / 4;
        int starty = height / 3;
        int endx = width / 4;
        int endy = height / 3;
        @SuppressWarnings("rawtypes")
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startx, starty).moveTo(endx, endy).release().perform();
    }

    // 点击 按钮 release() 结束的行动取消屏幕上的指针。
    public void Press(AppiumDriver<WebElement> driver, int x, int y) {
        new TouchAction(driver).press(x, y).release().perform();
    }

    // 长按
    public void longPress(AppiumDriver<WebElement> driver, WebElement webElement) {
        new TouchAction(driver).longPress(webElement).perform().release();
    }

    //点击按钮控件
    public void tap(AppiumDriver<WebElement> driver, WebElement webElement) {
        new TouchAction(driver).tap(webElement).perform().release();
    }

    //暂停脚本执行 单位毫秒
    public void wait(AppiumDriver<WebElement> driver, int duration) {
        try {
            driver.wait(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //滚动操作
    public void scroolToElement(AppiumDriver<WebElement> driver, String str) {
        ((FindsByAndroidUIAutomator<WebElement>) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiScrollable(new UiSelector().scrollable(true).instance(0).ScrollIntiView("
                        + "new UiScrollable().textContains(\"" + str + "\").instance(0)) ))");
    }

    //截图操作
    public void takeShot(AppiumDriver<WebElement> driver, String filePath) {
        File screfile = driver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screfile, new File(filePath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //处理拖动
    public void drag(MobileElement startElement, MobileElement endElement, AppiumDriver<WebElement> driver) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startElement).perform();
        touchAction.moveTo(endElement).release().perform();
    }

    //放大处理
    public void Zoomout(AppiumDriver<WebElement> driver, int x, int y) {
        MultiTouchAction multitouch = new MultiTouchAction(driver);
        int screnHeight = driver.manage().window().getSize().getHeight();
        int Yoffset = 100;
        if (y - 100 < 0) {
            Yoffset = y;
        } else if (y + 100 > screnHeight) {
            Yoffset = screnHeight - y;
        }
        TouchAction touchAction0 = new TouchAction(driver).press(x, y).moveTo(x, y - Yoffset).release();
        TouchAction touchAction1 = new TouchAction(driver).press(x, y).moveTo(x, y + Yoffset).release();
        multitouch.add(touchAction0);
        multitouch.add(touchAction1);
        multitouch.perform();
    }
}
