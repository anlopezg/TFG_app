package es.udc.paproject.backend.model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {


    @Value("${stripe.key.secret}")
    private String secretKey;

    @Value("${stripe.key.public}")
    private String publicKey;
    @PostConstruct
    public void setup() {
        Stripe.apiKey = secretKey;
    }
}
