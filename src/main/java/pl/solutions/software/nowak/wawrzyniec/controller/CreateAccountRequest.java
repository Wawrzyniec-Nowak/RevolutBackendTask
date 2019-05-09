package pl.solutions.software.nowak.wawrzyniec.controller;

public class CreateAccountRequest {

    private final long id;

    private final String money;

    public CreateAccountRequest(long id, String money) {
        this.id = id;
        this.money = money;
    }

    public long getId() {
        return id;
    }

    public String getMoney() {
        return money;
    }
}
