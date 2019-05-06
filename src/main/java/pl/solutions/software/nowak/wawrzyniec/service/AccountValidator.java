package pl.solutions.software.nowak.wawrzyniec.service;

import pl.solutions.software.nowak.wawrzyniec.model.Account;

import java.math.BigDecimal;

public class AccountValidator {

    public static boolean validateBalance(Account sender, BigDecimal amount) {
        if (sender.getBalance().compareTo(amount) < 0) {
            return false;
        }
        return true;
    }
}
