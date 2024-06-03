package es.udc.paproject.backend.rest.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import es.udc.paproject.backend.model.services.StripeService;
import es.udc.paproject.backend.rest.dtos.StripeAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create")
    public String createStripePayment(@RequestBody Map<String, Object> data) throws Exception {
        int amount = (int) data.get("amount");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", "usd");
        params.put("payment_method_types", List.of("card"));

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent.toJson();
    }

    /*

    @PostMapping("/createAccount")
    public StripeAccountDto createAccount(@RequestParam User user) throws StripeException {
        Account account = stripeService.createFullConnectedAccount(user);
        return StripeAccountDto.stripeAccountToDto(account);
    }*/

    @GetMapping("/accounts/{accountId}")
    public StripeAccountDto getAccount(@PathVariable String accountId) throws Exception {

        Account account =  stripeService.getAccount(accountId);

        return StripeAccountDto.stripeAccountToDto(account);
    }

    @PostMapping("/createAccountLink")
    public String createAccountLink(@RequestParam String accountId) throws StripeException {
        return stripeService.createAccountLink(accountId);
    }
}
