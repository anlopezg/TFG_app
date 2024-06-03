package es.udc.paproject.backend.model.services;

import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.*;
import es.udc.paproject.backend.model.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeServiceImpl implements StripeService{


    private static final Logger logger = LoggerFactory.getLogger(StripeService.class);

    @Override
    public Account createConnectedAccount(String email) throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCountry("ES")
                .setEmail(email)
                .build();

        return Account.create(params);
    }

    @Override
    public String createAccountLink(String accountId) throws StripeException {
        AccountLinkCreateParams params = AccountLinkCreateParams.builder()
                .setAccount(accountId)
                .setRefreshUrl("http://localhost:5173/users/becomeSellerStripe")
                .setReturnUrl("http://localhost:5173/users/becomeSellerStripe")
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build();

        AccountLink accountLink = AccountLink.create(params);
        return accountLink.getUrl();
    }


    @Override
    public PaymentIntent createPaymentIntent(Long amount, String currency, String stripeAccountId, String paymentMethodId) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setPaymentMethod(paymentMethodId)
                .setConfirm(true)
                .setOnBehalfOf(stripeAccountId)
                .build();


        PaymentIntent paymentIntent= PaymentIntent.create(params);

        System.out.println("Created payment intent"+ paymentIntent );

        return paymentIntent;
    }

    @Override

    public PaymentIntent confirmPaymentIntent(String paymentIntentId, String paymentMethodId, String accountId) throws StripeException {
        logger.info("Confirming PaymentIntent: {}", paymentIntentId);

        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
                .setPaymentMethod(paymentMethodId)
                .build();

        PaymentIntent confirmedPaymentIntent = paymentIntent.confirm(confirmParams, RequestOptions.builder()
                .setStripeAccount(accountId)
                .build());

        System.out.println("Confirmed PaymentIntent: "+ confirmedPaymentIntent.getId());

        return confirmedPaymentIntent;

    }


    @Override
    public Transfer createTransfer(int amount, String currency, String connectedAccountId) throws StripeException {
        TransferCreateParams params = TransferCreateParams.builder()
                .setAmount((long) amount)
                .setCurrency(currency)
                .setDestination(connectedAccountId)
                .build();

        return Transfer.create(params);
    }

    @Override
    public Account getAccount(String accountId) throws StripeException {
        return Account.retrieve(accountId);
    }


    @Override
    public Account createFullConnectedAccount(User user) throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCountry("US")
                .setEmail(user.getEmail())
                .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                .setIndividual(AccountCreateParams.Individual.builder()
                        .setFirstName(user.getFirstName())
                        .setLastName("Rosen")
                        .setDob(AccountCreateParams.Individual.Dob.builder()
                                .setDay(1L)
                                .setMonth(1L)
                                .setYear(1990L)
                                .build())
                        .setAddress(AccountCreateParams.Individual.Address.builder()
                                .setLine1("1234 Main Street")
                                .setCity("San Francisco")
                                .setState("CA")
                                .setPostalCode("94111")
                                .setCountry("US")
                                .build())
                        .build())
                .build();

        return Account.create(params);
    }
}
