package pl.solutions.software.nowak.wawrzyniec.model.store;

import pl.solutions.software.nowak.wawrzyniec.model.Account;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryDatabase {

    private final List<Account> accounts;

    public InMemoryDatabase() {
        this.accounts = new CopyOnWriteArrayList<>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
