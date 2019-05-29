package com.sauceservice;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class SauceServiceImpl implements SauceService {

    private static final String resourceUrl = "http://localhost:8080/spring-rest/foos";

    @Override
    public String solution(String input) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate
            .getForObject(resourceUrl + "/1", Foo.class);



        return "lol";
    }
}
