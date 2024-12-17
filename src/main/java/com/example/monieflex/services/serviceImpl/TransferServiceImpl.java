package com.example.monieflex.services.serviceImpl;

import com.example.monieflex.dto.response.BankResponse;
import com.example.monieflex.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final RestTemplate restTemplate;

    private static final String FLUTTERWAVE_BASE_URL = "https://api.flutterwave.com/v3";
    private static final String BEARER_TOKEN = "FLWSECK_TEST-SANDBOXDEMOKEY-X";

    @Override
    public BankResponse getAllBanks(String country) {
        String url = FLUTTERWAVE_BASE_URL + "/banks/" + country;

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(BEARER_TOKEN);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            // Make the GET request
            ResponseEntity<BankResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    BankResponse.class
            );

            // Return the response body
            return response.getBody();
        } catch (Exception e) {
            // Handle errors (log, rethrow, or handle gracefully)
            throw new RuntimeException("Error fetching banks: " + e.getMessage());
        }
    }
}

