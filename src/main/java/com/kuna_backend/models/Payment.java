package com.kuna_backend.models;

import com.kuna_backend.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table (name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "amount", nullable = false)
    private Float amount;

    @Column (name = "currency", length = 32, nullable = false)
    @Enumerated (EnumType.STRING)
    private String currency;

    @Column (name = "stripe_id", length = 256, nullable = false)
    private String stripeToken;

    @Column (name = "payment_status", length = 64, nullable = false)
    @Enumerated (EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column (name = "provider", length = 64, nullable = false)
    private String provider;

    @Column (name = "payment_date", nullable = false)
    @CreationTimestamp
    private Date paymentDate;

    @Column (name = "last_update", nullable = false)
    @UpdateTimestamp
    private Date lastUpdate;

    // Many-to-one relationship with Client //
    @ManyToOne ()
    @JoinColumn (name = "client_id", referencedColumnName = "id")
    private Client client;

    // One-to-one relationship with Order //
    @OneToOne(mappedBy = "payment")
    private Order order;


    public Payment(Integer id, Float amount, String currency, String stripeToken, PaymentStatus paymentStatus, String provider, Date paymentDate, Date lastUpdate, Client client, Order order) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
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
