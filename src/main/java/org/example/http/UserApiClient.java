package org.example.http;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserApiClient {

    private final RestTemplate restTemplate;

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final String URL_DELETE = URL + "/{id}";

    public ResponseEntity<String> getUsers(HttpHeaders headers) {
        HttpEntity<Void> request = new HttpEntity<>(headers);
        return restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
    }

    public ResponseEntity<String> createUser(User userDto, HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity(userDto, headers);
        return restTemplate.exchange(URL, HttpMethod.POST, request, String.class);
    }

    public ResponseEntity<String> updateUser(User userDto, HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity(userDto, headers);
        return restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);
    }

    public ResponseEntity<String> deleteUser(Long id, HttpHeaders headers) {
        HttpEntity<Void> request = new HttpEntity(headers);
        return restTemplate.exchange(URL_DELETE, HttpMethod.DELETE, request, String.class, id);
    }






}
