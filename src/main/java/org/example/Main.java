package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class Main {

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final String URL_DELETE = URL + "/{id}";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<> (headers);

        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                request,
                String.class
        );

        String cookieHeader = response.getHeaders().getFirst("Set-cookie");

        if (cookieHeader == null) {
            log.error("Set-cookie header not found");
            throw new IllegalStateException("Set-cookie header not found");
        }
            String sessionId = cookieHeader.split(";")[0];
            log.debug("Session id: {} ", sessionId);

        headers.add("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        User userDtoCreate = new User(3L, "James", "Brown", (byte) 25);

        HttpEntity<User> postRequest = new HttpEntity<> (userDtoCreate, headers);

        ResponseEntity<String> postResponse = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                postRequest,
                String.class
        );

        String part1 = postResponse.getBody();
        log.debug("Part1: {}", part1);

        User userDtoUpdate = new User(3L, "Thomas", "Shelby", (byte) 25);

        HttpEntity<User> updateRequest = new HttpEntity<> (userDtoUpdate, headers);

        ResponseEntity<String> updateResponse = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                updateRequest,
                String.class
        );

        String part2 = updateResponse.getBody();
        log.debug("Part2: {}", part2);

        HttpEntity<Void> deleteRequest = new HttpEntity<> (headers);

        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                URL_DELETE,
                HttpMethod.DELETE,
                deleteRequest,
                String.class,
                3
        );

        String part3 = deleteResponse.getBody();
        log.debug("Part3: {}", part3);
        log.info("Code: {} {} {}", part1, part2, part3);
    }
}
