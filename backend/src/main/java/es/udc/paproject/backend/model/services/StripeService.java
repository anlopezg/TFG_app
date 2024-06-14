package es.udc.paproject.backend.model.services;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Transfer;
import es.udc.paproject.backend.model.entities.User;

public interface StripeService {

    /**
     * Redirects the user to Stripe, to complete the configuration of their account
     * @param accountId The user's stripe account id
     * @return URL to redirect to
     * @throws StripeException
     */
    String createAccountLink(String accountId) throws StripeException;

    PaymentIntent createPaymentIntent(Long amount, String currency, String connectedAccountId, String paymentMethodId) throws StripeException;

    Account getAccount(String accountId) throws StripeException;

    Account createStripeAccount(User user) throws StripeException;
}
