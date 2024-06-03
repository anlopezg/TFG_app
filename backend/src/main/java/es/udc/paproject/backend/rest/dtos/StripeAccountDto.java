package es.udc.paproject.backend.rest.dtos;

import com.stripe.model.Account;

public class StripeAccountDto {

    private String id;
    private String email;

    private String businessType;
    private Boolean chargesEnabled;
    private Boolean payoutsEnabled;
    private String country;

    public StripeAccountDto() {
    }

    public StripeAccountDto(String id, String email, String businessType, Boolean chargesEnabled, Boolean payoutsEnabled, String country) {
        this.id = id;
        this.email = email;
        this.businessType = businessType;
        this.chargesEnabled = chargesEnabled;
        this.payoutsEnabled = payoutsEnabled;
        this.country = country;
    }

    public static StripeAccountDto stripeAccountToDto(Account account){
        return new StripeAccountDto(account.getId(), account.getEmail(), account.getBusinessType(), account.getChargesEnabled(),
                account.getPayoutsEnabled(), account.getCountry());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Boolean getChargesEnabled() {
        return chargesEnabled;
    }

    public void setChargesEnabled(Boolean chargesEnabled) {
        this.chargesEnabled = chargesEnabled;
    }

    public Boolean getPayoutsEnabled() {
        return payoutsEnabled;
    }

    public void setPayoutsEnabled(Boolean payoutsEnabled) {
        this.payoutsEnabled = payoutsEnabled;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
