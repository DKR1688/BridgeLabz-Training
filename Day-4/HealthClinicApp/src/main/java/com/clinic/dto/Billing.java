package com.clinic.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Billing {
    private int billId;
    private int appointmentId;
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDateTime billingDate;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int value) {
        billId = value;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int value) {
        appointmentId = value;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal value) {
        amount = value;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String value) {
        paymentStatus = value;
    }

    public LocalDateTime getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDateTime value) {
        billingDate = value;
    }

    @Override
    public String toString() {
        return "Billing{id=" + billId + ", appointmentId=" + appointmentId + ", amount=" + amount + ", status='"
                + paymentStatus + "', date=" + billingDate + "}";
    }
}
