package github.pedrozaz.pricealert.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import github.pedrozaz.pricealert.exception.ScrapperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScrapperService {

    @Autowired
    private StoreSelectorService storeSelectorService;

    public String findPrice(String url, String storeName) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            String selector = storeSelectorService.getPriceSelector(storeName);

            page.navigate(url);
            page.waitForSelector(selector);

            String price = page.locator(selector).first().textContent().trim();

            browser.close();
            playwright.close();
            return price;

        } catch (Exception e) {
           e.printStackTrace();
           throw new ScrapperException("Error while fetching product price");
        }
    }

    public String findProductName(String url, String storeName) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            String selector = storeSelectorService.getProductNameSelector(storeName);

            page.navigate(url);
            page.waitForSelector(selector);

            String productName = page.locator(selector).first().textContent().trim();

            browser.close();
            playwright.close();
            return productName;

        } catch (Exception e) {
           e.printStackTrace();
           throw new ScrapperException("Error while fetching product name");
        }
    }
}
