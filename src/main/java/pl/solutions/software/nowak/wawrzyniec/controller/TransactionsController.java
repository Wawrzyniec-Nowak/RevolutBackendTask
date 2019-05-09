package pl.solutions.software.nowak.wawrzyniec.controller;

import com.google.gson.Gson;
import pl.solutions.software.nowak.wawrzyniec.model.Account;
import pl.solutions.software.nowak.wawrzyniec.service.AccountDao;
import pl.solutions.software.nowak.wawrzyniec.service.AccountValidator;

import java.math.BigDecimal;

import static spark.Spark.post;

public class TransactionsController {

    private final AccountDao accountDao;

    private TransactionsController(AccountDao accountDao) {
        this.accountDao = accountDao;

        post("/transaction", //
                (request, response) -> {
                    TransferMoneyRequest req = new Gson().fromJson(request.body(), TransferMoneyRequest.class);
                    return this.transfer(req.getSender(), req.getRecipient(), req.getAmount());
                }, //
                response -> new Gson().toJson(response));
    }

    private static TransactionsController INSTANCE = null;

    public static TransactionsController getInstance(AccountDao accountDao) {
        if (INSTANCE == null) {
            synchronized (TransactionsController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TransactionsController(accountDao);
                }
            }
        }
        return INSTANCE;
    }

    Response transfer(long from, long to, String amount) {
        Account sender;
        Account recipient;
        try {
            sender = findAccount(from);
            recipient = findAccount(to);
        } catch (IllegalStateException e) {
            return new Response(Response.Status.FAILURE, e.getMessage());
        }

        if (!AccountValidator.validateBalance(sender, new BigDecimal(amount))) {
            return new Response(Response.Status.FAILURE, "Not enough cash to transfer");
        }
        sender.transfer(recipient, new BigDecimal(amount));

        return new Response(Response.Status.SUCCESS, "Cash successfully transferred");
    }

    private Account findAccount(long id) {
        return accountDao.findById(id).orElseThrow(() -> new IllegalStateException("Missing account with id " + id));
    }
}
