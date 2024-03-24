package com.patika.kredinbizdeservice.controller;

import com.patika.kredinbizdeservice.model.Bank;
import com.patika.kredinbizdeservice.model.Campaign;
import com.patika.kredinbizdeservice.model.CreditCard;
import com.patika.kredinbizdeservice.service.IBankService;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/banks")
public class BankController {
    
    @Autowired
    private IBankService bankService;
    
    @Autowired
    public BankController(IBankService bankService) {
        this.bankService = bankService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bank create(@RequestBody Bank bank) {
        System.out.println("bankService : " + bankService.hashCode());
        return bankService.save(bank);
    }
    
    @GetMapping
    public List<Bank> getAll() {
        return bankService.getAll();
    }
    
    @GetMapping("/{name}")
    public Bank getByName(@PathVariable String name) {
        return bankService.getByName(name);
    }
    
    @GetMapping("/listAllcreditCards")
    public List<CreditCard> listAllCreditCards() {
        return bankService.getAll().stream()
                .filter(bank -> bank.getCreditCards() != null)
                .flatMap(bank -> bank.getCreditCards().stream()).toList();
    }

    @GetMapping("/listAllCampaignsChronogically")
    public List<Campaign> listAllCampaignsChronogically() {
        return bankService.getAll().stream()
                .filter(bank -> bank.getCreditCards() != null)
                .flatMap(bank -> bank.getCreditCards().stream()
                        .flatMap(creditCard -> creditCard.getCampaignList().stream()
                                .sorted(Comparator.comparing(Campaign::getDueDate))))
                .toList();
    }
}
