package pl.solutions.software.nowak.wawrzyniec.controller;

public class TransferMoneyRequest {

    private final long sender;

    private final long recipient;

    private final String amount;

    public TransferMoneyRequest(long sender, long recipient, String amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public long getSender() {
        return sender;
    }

    public long getRecipient() {
        return recipient;
    }

    public String getAmount() {
        return amount;
    }
}
