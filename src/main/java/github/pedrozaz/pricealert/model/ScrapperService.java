package github.pedrozaz.pricealert.model;

public interface ScrapperService {
    public String findPrice(String url, String selector);
}
