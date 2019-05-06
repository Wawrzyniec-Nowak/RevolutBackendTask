package pl.solutions.software.nowak.wawrzyniec.model;

import java.math.BigDecimal;

public class Account {

    private final long id;

    private BigDecimal balance;

    private Account(long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public synchronized void transfer(Account recipient, BigDecimal amount) {
        recipient.changeBalance(amount);
        this.changeBalance(amount.multiply(BigDecimal.valueOf(-1L)));
    }

    public synchronized void changeBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long id;

        private BigDecimal balance;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Account build() {
            return new Account(id, balance);
        }
    }

    public long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
