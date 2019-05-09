package pl.solutions.software.nowak.wawrzyniec.controller;

import com.google.gson.Gson;
import pl.solutions.software.nowak.wawrzyniec.model.Account;
import pl.solutions.software.nowak.wawrzyniec.service.AccountDao;

import static spark.Spark.get;
import static spark.Spark.post;

public class AccountsController {

    private final AccountDao accountDao;

    private AccountsController(AccountDao accountDao) {
        this.accountDao = accountDao;

        post("/account", //
                (request, response) -> {
                    CreateAccountRequest req = new Gson().fromJson(request.body(), CreateAccountRequest.class);
                    return this.create(req.getId(), req.getMoney());
                }, //
                response -> new Gson().toJson(response));

        get("/account/balance", //
                (request, response) -> {
                    long id = Long.valueOf(request.queryParams("id"));
                    return this.balance(id);
                }, //
                response -> new Gson().toJson(response));
    }

    Response balance(long id) {
        return accountDao.findById(id) //
                .map(Account::getBalance) //
                .map(balance -> new Response(Response.Status.SUCCESS, "Balance is " + balance)) //
                .orElse(new Response(Response.Status.FAILURE, "Cannot get balance for this account"));
    }

    Response create(long id, String money) {
        try {
            accountDao.save(id, money);
        } catch (IllegalStateException e) {
            return new Response(Response.Status.FAILURE, e.getMessage());
        }
        return new Response(Response.Status.SUCCESS, "Account successfully created");
    }

    private static AccountsController INSTANCE = null;

    public static AccountsController getInstance(AccountDao accountDao) {
        if (INSTANCE == null) {
            synchronized (AccountsController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountsController(accountDao);
                }
            }
        }
        return INSTANCE;
    }

}
