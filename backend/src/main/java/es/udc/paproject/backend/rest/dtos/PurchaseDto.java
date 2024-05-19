package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseDto {

    private Long id;
    private List<PurchaseItemDto> items;
    private long date;
    private BigDecimal totalPrice;

    private String postalAddress;

    private String locality;
    private String region;
    private String country;
    private String postalCode;



    public PurchaseDto() {}

    public PurchaseDto(Long id, List<PurchaseItemDto> items, long date, BigDecimal totalPrice, String postalAddress,
                       String locality, String region, String country, String postalCode) {

        this.id = id;
        this.items = items;
        this.date = date;
        this.totalPrice = totalPrice;
        this.postalAddress = postalAddress;
        this.locality = locality;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PurchaseItemDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemDto> items) {
        this.items = items;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
