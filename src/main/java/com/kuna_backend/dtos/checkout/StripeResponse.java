package com.kuna_backend.dtos.checkout;

public class StripeResponse {
    private String stripeToken;

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public StripeResponse(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public StripeResponse() {
    }
}
