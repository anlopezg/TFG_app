package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.NotNull;

public class PaymentParamsDto {

    private String paymentMethodId;

    private String stripeAccountId;

    @NotNull
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getStripeAccountId() {
        return stripeAccountId;
    }

    public void setStripeAccountId(String stripeAccountId) {
        this.stripeAccountId = stripeAccountId;
    }
}
