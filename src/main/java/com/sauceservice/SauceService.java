package com.sauceservice;

import org.springframework.web.client.RestClientException;

public interface SauceService {
    String solution(String input) throws RestClientException;
}
