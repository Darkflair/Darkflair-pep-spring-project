package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws Exception {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be blank.");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            throw new Exception("Username already exists.");
        }
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) throws Exception{
        String password = account.getPassword();
        Account existingAccount = accountRepository.findByUsername(account.getUsername());

        if(existingAccount == null || !existingAccount.getPassword().equals(password)){
              throw new Exception("Invalid username or password");
        }
        return existingAccount;
    }
}
