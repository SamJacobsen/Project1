package com.project1.models;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ReimbursementFull{

    private int id;
    private int userId;
    private String userName;
    private LocalDateTime requestDate;
    private LocalDate transactionDate;
    private BigDecimal amount;
    private String purpose;
    private ReimbursementStatus status;

    public ReimbursementFull() {
    }

    public ReimbursementFull(int id, int userId, LocalDateTime requestDate, LocalDate transactionDate, BigDecimal amount, String purpose, ReimbursementStatus status) {
        this.id = id;
        this.userId = userId;
        this.requestDate = requestDate;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.purpose = purpose;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ReimbursementFull{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", requestDate=" + requestDate +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", purpose='" + purpose + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementFull that = (ReimbursementFull) o;
        return id == that.id && userId == that.userId && Objects.equals(userName, that.userName) && Objects.equals(requestDate, that.requestDate) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(amount, that.amount) && Objects.equals(purpose, that.purpose) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, userName, requestDate, transactionDate, amount, purpose, status);
    }
}
