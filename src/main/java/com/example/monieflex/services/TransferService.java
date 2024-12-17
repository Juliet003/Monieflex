package com.example.monieflex.services;

import com.example.monieflex.dto.response.BankResponse;


public interface TransferService {


    public BankResponse getAllBanks(String country);
}
