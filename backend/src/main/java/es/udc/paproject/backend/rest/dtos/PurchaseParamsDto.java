package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PurchaseParamsDto {

    private String postalAddress;
    private String locality;
    private String region;
    private String country;
    private String postalCode;

    @NotNull
    @Size(min=1, max=200)
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress.trim();
    }


    @NotNull
    @Size(min=1, max=60)
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @NotNull
    @Size(min=1, max=60)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotNull
    @Size(min=1, max=60)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull
    @Size(min=1, max=20)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode.trim();
    }
}
