package com.tekarch.accountmanagement.Controller;

import com.tekarch.accountmanagement.Models.Account;
import com.tekarch.accountmanagement.Services.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;


    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

//    @GetMapping("/test")
//    public String testEndpoint() {
//        return "Account-Server is running!";
//    }


    // 1. Create Account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // 2. Update Account by AccountID
    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account accountDetails) {
        Account updatedAccount = accountService.updateAccount(accountId, accountDetails);
        if (updatedAccount != null) {
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 3. Update Account by UserID and AccountID as Query Params
    @PutMapping
    public ResponseEntity<Account> updateAccountByUserIdAndAccountId(@RequestParam Long userId, @RequestParam Long accountId, @RequestBody Account accountDetails) {
        Optional<Account> account = accountService.getAccountByUserIdAndAccountId(userId, accountId);
        if (account.isPresent()) {
            Account updatedAccount = accountService.updateAccount(accountId, accountDetails);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 4. Retrieve All User Accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // 5. Retrieve Single User Accounts by UserID
    @GetMapping("/user")
    public ResponseEntity<List<Account>> getAccountsByUserId(@RequestParam Long userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // 6. Retrieve Account by AccountID
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Optional<Account> account = accountService.getAccountById(accountId);
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 7. Delete Account by AccountID
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        boolean isDeleted = accountService.deleteAccount(accountId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 8. Retrieve Balances for Multiple Accounts by UserID
    @GetMapping("/balance")
    public ResponseEntity<List<Account>> getAccountsBalanceByUserId(@RequestParam Long userId) {
        List<Account> accounts = accountService.getAccountsBalanceByUserId(userId);
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}