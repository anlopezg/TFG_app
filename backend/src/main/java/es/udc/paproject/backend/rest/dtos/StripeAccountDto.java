package es.udc.paproject.backend.rest.dtos;

import com.stripe.model.Account;

public class StripeAccountDto {

    private String id;
    private Boolean chargesEnabled;
    private Boolean payoutsEnabled;
    private String country;

    private String defaultCurrency;

    public StripeAccountDto() {
    }

    public StripeAccountDto(String id, Boolean chargesEnabled, Boolean payoutsEnabled, String country, String defaultCurrency) {
        this.id = id;
        this.chargesEnabled = chargesEnabled;
        this.payoutsEnabled = payoutsEnabled;
        this.country = country;
        this.defaultCurrency = defaultCurrency;
    }

    public static StripeAccountDto stripeAccountToDto(Account account){
        return new StripeAccountDto(account.getId(), account.getChargesEnabled(),
                account.getPayoutsEnabled(), account.getCountry(), account.getDefaultCurrency());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
}
