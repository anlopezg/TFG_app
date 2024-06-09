package es.udc.paproject.backend.test.model.services;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserAlreadySellerException;
import es.udc.paproject.backend.model.services.StripeService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StripeServiceTest {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private UserService userService;

    @Mock
    private PaymentIntent paymentIntent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser(String userName, String email) {
        return new User(userName, email,"password", "firstName", "language", "country", "region",
                1, 2, "long bio");
    }


    @Test
    public void testCreateTestConnectedAccount() throws StripeException, DuplicateInstanceException, IOException {

        String email = "seller@gmail.com";
        User seller = createUser("seller2" , email);
        userService.signUp(seller);

        Account account = stripeService.createStripeAccount(seller);
        assertNotNull(account);
        assertNotNull(account.getId());
        assertNotNull(account.getEmail());
    }

    @Test
    public void testGetTestConnectedAccount() throws StripeException, DuplicateInstanceException, IOException {

        String email = "seller@gmail.com";
        User seller = createUser("seller2" , email);
        userService.signUp(seller);

        Account createdAccount = stripeService.createStripeAccount(seller);
        Account retrievedAccount = stripeService.getAccount(createdAccount.getId());
        assertNotNull(retrievedAccount);
        assertNotNull(retrievedAccount.getId());
        assertNotNull(retrievedAccount.getEmail());
    }

    @Test
    public void testUserBecomesSellerAndCreateStripeAccount() throws UserAlreadySellerException, InstanceNotFoundException, StripeException, DuplicateInstanceException, IOException {

        String email = "seller@gmail.com";
        User seller = createUser("seller2" , email);
        userService.signUp(seller);

        String stripeAccountId  = userService.userBecomesSeller(seller.getId());

        assertNotNull(stripeAccountId);
        assertEquals(seller.getRole(), User.RoleType.SELLER);

        Account account = stripeService.getAccount(stripeAccountId);
        assertEquals(account.getEmail(), email);
    }

    /*
    @Test
    public void testCreatePaymentIntent() throws StripeException {
        // Arrange
        Long amount = 5000L; // 50.00 EUR in cents
        String currency = "eur";
        String accountId = "acct_test";

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setTransferData(PaymentIntentCreateParams.TransferData.builder()
                        .setDestination(accountId)
                        .build())
                .build();

        when(PaymentIntent.create(any(PaymentIntentCreateParams.class))).thenReturn(paymentIntent);

        // Act
        PaymentIntent result = stripeService.createPaymentIntent(amount, currency, accountId);

        // Assert
        ArgumentCaptor<PaymentIntentCreateParams> captor = ArgumentCaptor.forClass(PaymentIntentCreateParams.class);
        verify(PaymentIntent.class).create(captor.capture());
        PaymentIntentCreateParams capturedParams = captor.getValue();

        assertEquals(amount, capturedParams.getAmount());
        assertEquals(currency, capturedParams.getCurrency());
        assertEquals(accountId, capturedParams.getTransferData().getDestination());
        assertEquals(paymentIntent, result);
    }*/


}
