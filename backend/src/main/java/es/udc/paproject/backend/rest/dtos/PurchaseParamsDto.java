package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PurchaseParamsDto {

    private String postalAddress;
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
    @Size(min=1, max=20)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode.trim();
    }
}
