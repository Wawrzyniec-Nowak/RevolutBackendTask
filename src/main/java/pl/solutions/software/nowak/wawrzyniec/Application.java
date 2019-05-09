package pl.solutions.software.nowak.wawrzyniec;

import pl.solutions.software.nowak.wawrzyniec.controller.AccountsController;
import pl.solutions.software.nowak.wawrzyniec.controller.TransactionsController;
import pl.solutions.software.nowak.wawrzyniec.model.store.InMemoryDatabase;
import pl.solutions.software.nowak.wawrzyniec.service.AccountDao;

public class Application {

    private final static TransactionsController TRANSACTIONS_CONTROLLER = TransactionsController.getInstance(AccountDao.getInstance(new InMemoryDatabase()));

    private final static AccountsController ACCOUNTS_CONTROLLER = AccountsController.getInstance(AccountDao.getInstance(new InMemoryDatabase()));

    public static void main(String[] args) {
    }
}
