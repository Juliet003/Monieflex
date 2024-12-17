package com.example.monieflex.dto.response;

import com.example.monieflex.entities.Bank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BankResponse {
    private String status;
    private String message;
    private List<Bank> data;
}
