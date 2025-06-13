package github.pedrozaz.pricealert;

import github.pedrozaz.pricealert.service.ScrapperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		ScrapperService scrapperService = new ScrapperService();

		String price = scrapperService.findPrice("https://www.amazon.com.br/Apple-iPhone-16-128-GB/dp/B0DJFTCKNN/ref=sr_1_4?__mk_pt_BR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&sr=8-4", "a-price-whole");
		System.out.println(price);
	}

}
