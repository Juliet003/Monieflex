package com.example.monieflex.controller;

import com.example.monieflex.dto.response.BankResponse;
import com.example.monieflex.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transfer/")
public class TransferController {
    private final TransferService transferService;

    @GetMapping("/get_banks/{country}")
    public ResponseEntity<BankResponse> getBanks(@PathVariable String country) {
    BankResponse response = transferService.getAllBanks(country);
    return ResponseEntity.ok(response);
    }
}
