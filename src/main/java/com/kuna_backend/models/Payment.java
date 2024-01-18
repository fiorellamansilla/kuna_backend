package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table (name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "amount")
    private float amount;

    @Column (name = "currency", length = 32)
    private String currency;

    @Column (name = "stripe_id", length = 256)
    private String stripeToken;

    @Column (name = "payment_status", length = 64)
    private String paymentStatus;

    @Column (name = "provider", length = 64)
    private String provider;

    @Column (name = "payment_date")
    private Date paymentDate;

    @Column (name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    // Many-to-one relationship with Client //
    @ManyToOne ()
    @JoinColumn (name = "client_id", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    // One-to-one relationship with Order //
    @OneToOne(mappedBy = "payment")
    private Order order;

    public Payment(long id, float amount, String currency, String stripeToken, String paymentStatus, String provider, Date paymentDate, LocalDateTime lastUpdate, Client client, Order order) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.stripeToken = stripeToken;
        this.paymentStatus = paymentStatus;
        this.provider = provider;
        this.paymentDate = paymentDate;
        this.lastUpdate = lastUpdate;
        this.client = client;
        this.order = order;
    }

    public Payment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
