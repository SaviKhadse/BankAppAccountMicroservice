package com.tekarch.accountmanagement.Services;

import com.tekarch.accountmanagement.Config.UserDTO;
import com.tekarch.accountmanagement.Config.UserNotFoundException;
import com.tekarch.accountmanagement.Models.Account;
import com.tekarch.accountmanagement.Repositories.AccountRepository;
import com.tekarch.accountmanagement.Services.Interfaces.AccountService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;
    private final String userServiceUrl = "http://localhost:8081/users";

    public AccountServiceImpl(AccountRepository accountRepository, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.restTemplate = restTemplate;
    }

//    @Autowired
//    public AccountServiceImpl(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }


    @Override
    public Account createAccount(Account account) {
        //return accountRepository.save(account);
        // Check if user exists by making a GET request to User Microservice
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                userServiceUrl + "/" + account.getUserId(),
                HttpMethod.GET,
                null,
                UserDTO.class
        );

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new UserNotFoundException("User with ID " + account.getUserId() + " not found.");
        }

        // Proceed with account creation if user exists
        account.setCreatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long accountId, Account accountDetails) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            Account existingAccount = account.get();
            existingAccount.setAccountType(accountDetails.getAccountType());
            existingAccount.setBalance(accountDetails.getBalance());
            existingAccount.setCurrency(accountDetails.getCurrency());
            return accountRepository.save(existingAccount);
        }
        return null; // Or handle as you prefer (throw exception, etc.)
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Optional<Account> getAccountByUserIdAndAccountId(Long userId, Long accountId) {
        return accountRepository.findByUserIdAndAccountId(userId, accountId);
    }

    @Override
    public boolean deleteAccount(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.delete(account.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Account> getAccountsBalanceByUserId(Long userId) {
        //return accountRepository.findByUser_UserId(userId);
        return accountRepository.findByUserId(userId);
    }
}
