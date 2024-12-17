package com.tekarch.accountmanagement.Repositories;

import com.tekarch.accountmanagement.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    //List<Account> findByUser_UserId(Long userId);

    List<Account> findByUserId(Long userId);

    //Optional<Account> findByUser_UserIdAndAccountId(Long userId, Long accountId);

    Optional<Account> findByUserIdAndAccountId(Long userId, Long accountId);

}