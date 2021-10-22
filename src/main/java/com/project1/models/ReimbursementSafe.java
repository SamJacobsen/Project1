package com.project1.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ReimbursementSafe {

    private LocalDateTime requestDate;
    private LocalDate transactionDate;
    private BigDecimal amount;
    private String purpose;
    private ReimbursementStatus status;

    public ReimbursementSafe() {
        this.requestDate = LocalDateTime.now();
    }

    public ReimbursementSafe(LocalDateTime requestDate, LocalDate transactionDate, BigDecimal amount, String purpose, ReimbursementStatus status) {
        this.requestDate = requestDate;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.purpose = purpose;
        this.status = status;
    }

    public LocalDateTime getRequestDate() {return requestDate;}

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public ReimbursementStatus getStatus() {
        return status;
    }

    public void setStatus(ReimbursementStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "requestDate=" + requestDate +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", purpose='" + purpose + '\'' +
                ", status=" + status +
                '}';
    }
}
