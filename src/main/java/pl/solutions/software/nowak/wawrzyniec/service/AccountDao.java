package pl.solutions.software.nowak.wawrzyniec.service;

import pl.solutions.software.nowak.wawrzyniec.model.Account;
import pl.solutions.software.nowak.wawrzyniec.model.store.InMemoryDatabase;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountDao {

    private final InMemoryDatabase database;

    private static AccountDao INSTANCE = null;

    private AccountDao(InMemoryDatabase database) {
        this.database = database;
    }

    public static AccountDao getInstance(InMemoryDatabase database) {
        if (INSTANCE == null) {
            synchronized (AccountDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountDao(database);
                }
            }
        }
        return INSTANCE;
    }

    public void save(long id, String money) {
        if (findById(id).isPresent()) {
            throw new IllegalStateException("Account already exists");
        }
        database.addAccount(Account.builder().id(id).balance(new BigDecimal(money)).build());
    }

    public Optional<Account> findById(long id) {
        return database.getAccounts() //
                .stream() //
                .filter(account -> account.getId() == id) //
                .findFirst();
    }
}
