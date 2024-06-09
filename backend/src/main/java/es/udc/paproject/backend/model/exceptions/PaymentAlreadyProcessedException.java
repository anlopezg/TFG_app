package es.udc.paproject.backend.model.exceptions;

public class PaymentAlreadyProcessedException extends Exception{

    public PaymentAlreadyProcessedException(String message) {
        super(message);
    }
}
