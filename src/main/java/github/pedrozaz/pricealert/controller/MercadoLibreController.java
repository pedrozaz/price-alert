package github.pedrozaz.pricealert.controller;

import github.pedrozaz.pricealert.entities.Alert;
import github.pedrozaz.pricealert.entities.Product;
import github.pedrozaz.pricealert.model.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mercadolibre")
public class MercadoLibreController {

    @Autowired
    private ScrapperService scrapperService;

    @PostMapping()
    public Alert newAlert(@RequestBody Product product) {
        String price = alert(product).getProduct().getCurrentPrice();
        newAlert().setProduct();

    }
}
