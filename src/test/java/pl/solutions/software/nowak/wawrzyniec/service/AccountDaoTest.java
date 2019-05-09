package pl.solutions.software.nowak.wawrzyniec.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.solutions.software.nowak.wawrzyniec.MockDatabaseBaseTest;
import pl.solutions.software.nowak.wawrzyniec.model.Account;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountDaoTest extends MockDatabaseBaseTest {

    private final AccountDao accountDao = AccountDao.getInstance(inMemoryDatabase);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFindAccountInDatabase() {
        Optional<Account> account = accountDao.findById(1L);
        assertTrue(account.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalIfAccountNotFound() {
        Optional<Account> account = accountDao.findById(-1L);
        assertFalse(account.isPresent());
    }

    @Test
    public void shouldCreateAccountAndNotThrowException() {
        accountDao.save(10000, "10.0");
    }

    @Test
    public void shouldThrowExceptionWhenAccountAlreadyExists() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Account already exists");
        accountDao.save(1, "10.0");
    }
}