package pl.solutions.software.nowak.wawrzyniec.controller;

import org.junit.Test;
import pl.solutions.software.nowak.wawrzyniec.MockDatabaseBaseTest;
import pl.solutions.software.nowak.wawrzyniec.service.AccountDao;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class TransactionsControllerTest extends MockDatabaseBaseTest {

    private final TransactionsController controller = TransactionsController.getInstance(AccountDao.getInstance(inMemoryDatabase));
    private final AccountsController accountsController = AccountsController.getInstance(AccountDao.getInstance(inMemoryDatabase));

    @Test
    public void shouldTransferMoneySuccessfully() {
        loadMockData();
        Response response = controller.transfer(1, 2, "10");

        assertEquals(Response.Status.SUCCESS, response.getStatus());
    }

    @Test
    public void shouldNotTransferMoneyBecauseOfNotEnoughCash() {
        loadMockData();
        Response response = controller.transfer(1, 2, "100");

        assertEquals(Response.Status.FAILURE, response.getStatus());
        assertEquals("Not enough cash to transfer", response.getMessage());
    }

    @Test
    public void shouldNotTransferMoneyBecauseOfMissingAccount() {
        Response response = controller.transfer(-1, 2, "100");

        assertEquals(Response.Status.FAILURE, response.getStatus());
        assertEquals("Missing account with id -1", response.getMessage());
    }

    @Test
    public void shouldTransferMoneyConcurrently() throws InterruptedException {
        CountDownLatch accounts = new CountDownLatch(ACCOUNTS_COUNTER);
        ExecutorService executor = Executors.newFixedThreadPool(ACCOUNTS_COUNTER);

        for (int i = 0; i < ACCOUNTS_COUNTER; i++) {
            int from = i;
            int to = i + 1;
            executor.execute(() -> {
                controller.transfer(from, to, "10");
                accounts.countDown();
            });
        }
        accounts.await();
        executor.shutdown();

        for (int i = 0; i < ACCOUNTS_COUNTER; i++) {
            Response response = accountsController.balance(i);
            assertEquals(Response.Status.SUCCESS, response.getStatus());
        }
    }
}