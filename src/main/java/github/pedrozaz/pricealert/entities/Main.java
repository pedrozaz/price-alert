package github.pedrozaz.pricealert.entities;

import github.pedrozaz.pricealert.service.ScrapperService;

public class Main {
    public static void main(String[] args) {
        ScrapperService scrapperService = new ScrapperService();
        MercadoLibreScrapperService mercadoService = new MercadoLibreScrapperService();

        String amazonLink = "https://www.amazon.com.br/dp/B0DJFTCKNN/";
        String kabumLink = "https://www.kabum.com.br/produto/623774";
        String mercadolibreLink = "https://www.mercadolivre.com.br/p/MLB37939837";


        String price = scrapperService.findPrice(amazonLink, "span.a-price-whole");
        //String price2= scrapperService.findPrice(url2, "span.oldPrice");
        String price3 = scrapperService.findPrice(kabumLink, "h4.finalPrice");
        String librePrice = mercadoService.findPrice(mercadolibreLink, "div.ui-pdp-price__second-line span.andes-money-amount__fraction");

        System.out.println("Product price: R$" + price);
        //System.out.println("Product old price: R$" + price2);
        System.out.println("Product new price: R$" + price3);

        Alert alert = new Alert(1L, price3);
        Product iphone = new Product("Monitor", librePrice, alert.getPrice(), 1000.00 );
        System.out.println(iphone);


        System.out.println("Fim do programa");
    }
}
