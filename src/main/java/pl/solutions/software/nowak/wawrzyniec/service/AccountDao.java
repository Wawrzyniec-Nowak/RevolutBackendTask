package pl.solutions.software.nowak.wawrzyniec.service;

import pl.solutions.software.nowak.wawrzyniec.model.Account;
import pl.solutions.software.nowak.wawrzyniec.model.store.InMemoryDatabase;

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

    public Optional<Account> findById(long id) {
        return database.getAccounts() //
                .stream() //
                .filter(account -> account.getId() == id) //
                .findFirst();
    }
}
