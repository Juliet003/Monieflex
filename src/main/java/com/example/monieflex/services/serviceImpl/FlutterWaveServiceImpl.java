package com.example.monieflex.services.serviceImpl;

import com.example.monieflex.dto.request.VirtualAccountRequest;
import com.example.monieflex.services.FlutterWaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class FlutterWaveServiceImpl implements FlutterWaveService {

    private final RestTemplate restTemplate;



    @Override
    public Map<String, Object> createVirtualAccount(VirtualAccountRequest request) {

        String url = "https://api.flutterwave.com/v3/virtual-account-numbers";

            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth("FLWSECK_TEST-SANDBOXDEMOKEY-X");

            // Request body (directly using the DTO object)
            HttpEntity<VirtualAccountRequest> httpRequest = new HttpEntity<>(request, headers);

            try {
                // Make API call
                ResponseEntity<Map> response = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        httpRequest,
                        Map.class
                );

                // Handle successful response
                log.info( "response from flutterwave " +  response.getBody().toString());
                return response.getBody();
            } catch (Exception e) {
                // Handle errors (log, rethrow, or handle gracefully)
                throw new RuntimeException("Error creating virtual account: " + e.getMessage());
            }
        }
    }

