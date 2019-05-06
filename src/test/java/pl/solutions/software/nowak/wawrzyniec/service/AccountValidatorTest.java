package pl.solutions.software.nowak.wawrzyniec.service;

import org.junit.Test;
import pl.solutions.software.nowak.wawrzyniec.model.Account;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountValidatorTest {

    @Test
    public void shouldReturnFalseIfAccountDoesNotHaveEnoughMoney() {
        Account account = Account.builder() //
                .balance(BigDecimal.valueOf(10)) //
                .build();
        boolean result = AccountValidator.validateBalance(account, BigDecimal.valueOf(100));

        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueIfAccountHasEnoughMoney() {
        Account account = Account.builder() //
                .balance(BigDecimal.valueOf(100)) //
                .build();
        boolean result = AccountValidator.validateBalance(account, BigDecimal.valueOf(10));

        assertTrue(result);
    }
}