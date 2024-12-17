package com.example.monieflex.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class VirtualAccountRequest {
    private String email;
    private String bvn;
    private boolean isPermanent;
    private String narration;
    private String txRef;
    private int amount;

}
