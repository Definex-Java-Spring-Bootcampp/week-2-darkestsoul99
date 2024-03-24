package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.model.Bank;
import com.patika.kredinbizdeservice.repository.BankRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BankService implements IBankService {

    private BankRepository bankRepository = new BankRepository();
    
    @Override
    public Bank save(Bank bank) {
        bankRepository.save(bank);
        
        return bank;
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.getAll();
    }

    @Override
    public Bank getByName(String name) {
        Optional<Bank> foundBank = bankRepository.findByName(name);

        Bank bank = null;

        if (foundBank.isPresent()) {
            bank = foundBank.get();
        }

        return bank;
    }
}
