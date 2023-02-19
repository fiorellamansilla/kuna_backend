package com.kuna_backend.entities;

import com.kuna_backend.entities.enums.Currency;
import lombok.Data;

// Business Entity that we will use during the charge operation //
@Data
public class ChargeRequest {
    private String description;
    private Integer amount; // cents
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;

    public ChargeRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }
}
