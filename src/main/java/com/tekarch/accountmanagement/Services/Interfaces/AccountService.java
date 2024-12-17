package com.tekarch.accountmanagement.Services.Interfaces;

import com.tekarch.accountmanagement.Models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(Account account);

    Account updateAccount(Long accountId, Account accountDetails);

    List<Account> getAllAccounts();

    List<Account> getAccountsByUserId(Long userId);

    Optional<Account> getAccountById(Long accountId);

    Optional<Account> getAccountByUserIdAndAccountId(Long userId, Long accountId);

    boolean deleteAccount(Long accountId);

    List<Account> getAccountsBalanceByUserId(Long userId);


}
