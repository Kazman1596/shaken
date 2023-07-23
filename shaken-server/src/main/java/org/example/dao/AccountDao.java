package org.example.dao;

import org.example.model.RegisterUserDto;
import org.example.model.Account;

import java.util.List;

public interface AccountDao {
    //TODO: After server-side is done, we can jump to client and get this all hooked up

    List<Account> getAccounts();

    List<Account> searchAccounts(String input);

    Account getAccountById(int id);

    Account getAccountByUsername(String username);

    Account createAccount(RegisterUserDto user);
}

//