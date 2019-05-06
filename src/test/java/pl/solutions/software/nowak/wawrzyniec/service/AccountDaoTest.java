package pl.solutions.software.nowak.wawrzyniec.service;

import org.junit.Test;
import pl.solutions.software.nowak.wawrzyniec.MockDatabaseBaseTest;
import pl.solutions.software.nowak.wawrzyniec.model.Account;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountDaoTest extends MockDatabaseBaseTest {

    private static AccountDao accountDao = AccountDao.getInstance(inMemoryDatabase);

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
}