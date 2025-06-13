package github.pedrozaz.pricealert.entities;

import github.pedrozaz.pricealert.service.ScrapperService;

public class Main {
    public static void main(String[] args) {
        ScrapperService scrapperService = new ScrapperService();

        String url = "https://www.amazon.com.br/dp/B0DJFTCKNN/";
        String url2 = "https://www.kabum.com.br/produto/685212/";
        String price = scrapperService.findPrice(url, "span.a-price-whole");
        //String price2= scrapperService.findPrice(url2, "span.oldPrice");
        String price3 = scrapperService.findPrice(url2, "h4.finalPrice");

        System.out.println("Product price: R$" + price);
        //System.out.println("Product old price: R$" + price2);
        System.out.println("Product new price: R$" + price3);

        Alert alert = new Alert(1L, price3);
        Product iphone = new Product("Monitor", url2, alert.getPrice(), 1000.00 );
        System.out.println(iphone);


        System.out.println("Fim do programa");
    }
}
