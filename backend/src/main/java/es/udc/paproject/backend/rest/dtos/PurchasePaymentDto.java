package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class PurchasePaymentDto {

    private Long id;
    private BigDecimal totalPrice;

    private PurchasePaymentDto() {
    }

    public PurchasePaymentDto(Long id, BigDecimal totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
