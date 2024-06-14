package es.udc.paproject.backend.test.model.services;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.PaymentIntent;
import com.stripe.param.AccountLinkCreateParams;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserAlreadySellerException;
import es.udc.paproject.backend.model.services.StripeService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @Mock
    private AccountLink accountLinkMock;

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

    @Test
    void createAccountLink_ShouldReturnAccountLinkUrl() throws StripeException {
        // Arrange
        String accountId = "acct_123";
        String expectedUrl = "http://test-url";

        try (MockedStatic<AccountLink> mockedStatic = Mockito.mockStatic(AccountLink.class)) {
            mockedStatic.when(() -> AccountLink.create(any(AccountLinkCreateParams.class)))
                    .thenReturn(accountLinkMock);

            when(accountLinkMock.getUrl()).thenReturn(expectedUrl);

            // Act
            String resultUrl = stripeService.createAccountLink(accountId);

            // Assert
            assertEquals(expectedUrl, resultUrl);

            // Verify that the create method was called with the expected parameters
            mockedStatic.verify(() -> AccountLink.create(any(AccountLinkCreateParams.class)));
            verify(accountLinkMock).getUrl();
        }
    }


}
