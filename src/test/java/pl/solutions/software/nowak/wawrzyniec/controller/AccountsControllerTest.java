package pl.solutions.software.nowak.wawrzyniec.controller;

import org.junit.Test;
import pl.solutions.software.nowak.wawrzyniec.MockDatabaseBaseTest;
import pl.solutions.software.nowak.wawrzyniec.service.AccountDao;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class AccountsControllerTest extends MockDatabaseBaseTest {

    private final AccountsController controller = AccountsController.getInstance(AccountDao.getInstance(inMemoryDatabase));

    @Test
    public void shouldSuccessfullyCreateAccount() {
        Response response = controller.create(11111L, "10");

        assertEquals(Response.Status.SUCCESS, response.getStatus());
    }

    @Test
    public void shouldNotCreateAccountIfAlreadyExists() {
        controller.create(22222L, "100");
        Response response = controller.create(22222L, "100");

        assertEquals(Response.Status.FAILURE, response.getStatus());
        assertEquals("Account already exists", response.getMessage());
    }

    @Test
    public void shouldReturnBalanceOfTheAccount() {
        controller.create(33333L, "10.0");
        Response response = controller.balance(33333L);

        assertEquals(Response.Status.SUCCESS, response.getStatus());
        assertEquals("Balance is 10.0", response.getMessage());
    }

    @Test
    public void shouldNotReturnBalanceIfCannotFetchAccount() {
        Response response = controller.balance(44444L);

        assertEquals(Response.Status.FAILURE, response.getStatus());
        assertEquals("Cannot get balance for this account", response.getMessage());
    }

    @Test
    public void shouldCreateAccountsConcurrently() throws InterruptedException {
        CountDownLatch accounts = new CountDownLatch(ACCOUNTS_COUNTER);
        ExecutorService executor = Executors.newFixedThreadPool(ACCOUNTS_COUNTER);

        for (int i = 0; i < ACCOUNTS_COUNTER; i++) {
            int from = i;
            executor.execute(() -> {
                controller.create(from, "10");
                accounts.countDown();
            });
        }
        accounts.await();
        executor.shutdown();

        for (int i = 0; i < ACCOUNTS_COUNTER; i++) {
            Response response = controller.balance(i);
            assertEquals(Response.Status.SUCCESS, response.getStatus());
        }
    }
}