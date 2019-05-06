package pl.solutions.software.nowak.wawrzyniec.controller;

import org.junit.Test;
import pl.solutions.software.nowak.wawrzyniec.MockDatabaseBaseTest;
import pl.solutions.software.nowak.wawrzyniec.model.Account;
import pl.solutions.software.nowak.wawrzyniec.service.AccountDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TransactionsControllerTest extends MockDatabaseBaseTest {

    private static TransactionsController controller = TransactionsController.getInstance(AccountDao.getInstance(inMemoryDatabase));

    @Test
    public void shouldTransferMoneySuccessfully() {
        Response response = controller.transfer("1", "2", "10");

        assertEquals(Response.Status.SUCCESS, response.getStatus());
    }

    @Test
    public void shouldNotTransferMoneyBecauseOfNotEnoughCash() {
        Response response = controller.transfer("1", "2", "100");

        assertEquals(Response.Status.FAILURE, response.getStatus());
        assertEquals("Not enough cash to transfer", response.getMessage());
    }

    @Test
    public void shouldNotTransferMoneyBecauseOfMissingAccount() {
        Response response = controller.transfer("-1", "2", "100");

        assertEquals(Response.Status.FAILURE, response.getStatus());
        assertEquals("Missing account with id -1", response.getMessage());
    }

    @Test
    public void shouldTransferMoneyConcurrently() throws InterruptedException {
        long l = System.currentTimeMillis();
        CountDownLatch accounts = new CountDownLatch(ACCOUNTS_COUNTER);
        ExecutorService executor = Executors.newFixedThreadPool(ACCOUNTS_COUNTER);

        for (int i = 0; i < ACCOUNTS_COUNTER; i++) {
            String from = String.valueOf(i);
            String to = String.valueOf(i + 1);
            executor.execute(() -> {
                controller.transfer(from, to, "10");
                accounts.countDown();
            });
        }
        accounts.await();
        executor.shutdown();
        System.out.println(System.currentTimeMillis() - l);

        Map<BigDecimal, List<Account>> result = inMemoryDatabase.getAccounts() //
                .stream() //
                .collect(Collectors.groupingBy(Account::getBalance));

        assertEquals(998, result.get(BigDecimal.valueOf(10L)).size());
        assertEquals(1, result.get(BigDecimal.valueOf(20L)).size()); //last was trying to send to non exiting account
        assertEquals(1, result.get(BigDecimal.valueOf(0L)).size()); //first didn't get money
    }
}