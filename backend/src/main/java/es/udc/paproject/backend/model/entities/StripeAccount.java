package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

@Entity
public class StripeAccount {

    private Long id;
    private User user;
    private String stripeAccountId;
    private String stripeEmail;
    private String accountStatus;

    public StripeAccount() {
    }

    public StripeAccount(User user, String stripeAccountId, String stripeEmail) {
        this.user = user;
        this.stripeAccountId = stripeAccountId;
        this.stripeEmail = stripeEmail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    @OneToOne(optional=false, fetch= FetchType.LAZY)
    @JoinColumn(name= "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStripeAccountId() {
        return stripeAccountId;
    }

    public void setStripeAccountId(String stripeAccountId) {
        this.stripeAccountId = stripeAccountId;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
