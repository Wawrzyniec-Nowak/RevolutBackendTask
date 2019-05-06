package pl.solutions.software.nowak.wawrzyniec;

import org.junit.BeforeClass;
import pl.solutions.software.nowak.wawrzyniec.model.Account;
import pl.solutions.software.nowak.wawrzyniec.model.store.InMemoryDatabase;

import java.math.BigDecimal;

public abstract class MockDatabaseBaseTest {

    protected static final int ACCOUNTS_COUNTER = 1000;

    protected static final int INITIAL_MONEY = 10;

    protected static InMemoryDatabase inMemoryDatabase;

    @BeforeClass
    public static void init() {
        inMemoryDatabase = new InMemoryDatabase();
        for (int i = 0; i < ACCOUNTS_COUNTER; i++) {
            inMemoryDatabase.addAccount(Account.builder() //
                    .id(i) //
                    .balance(BigDecimal.valueOf(INITIAL_MONEY)) //
                    .build());
        }
    }
}
