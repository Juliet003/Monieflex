package com.example.monieflex.services;

import com.example.monieflex.dto.request.VirtualAccountRequest;

import java.util.Map;

public interface FlutterWaveService {


  public Map<String, Object> createVirtualAccount(VirtualAccountRequest request);

}
