package github.pedrozaz.pricealert.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import github.pedrozaz.pricealert.exception.ScrapperException;

public class ScrapperService {

    public String findPrice(String url, String selector) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            page.navigate(url);
            page.waitForSelector(selector);

            String price = page.locator(selector).first().textContent().trim();

            browser.close();
            playwright.close();
            return price;
        } catch (Exception e) {
           e.printStackTrace();
           throw new ScrapperException("Error");
        }
    }
}
