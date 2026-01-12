package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.http.UserApiClient;
import org.example.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceWork {

    private final UserApiClient userApi;

    public void runService() {
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> getResponse = userApi.getUsers(headers);
        String setCookie = getResponse.getHeaders().getFirst("Set-cookie");

        if (setCookie == null) {
            log.error("Set-cookie header not found");
            throw new IllegalStateException("Set-cookie header not found");
        }

        String sessionId = setCookie.split(";")[0];
        log.debug("Session id: {}", sessionId);

        headers.add(HttpHeaders.COOKIE, sessionId);

        User userPostDto = new User(3L, "James", "Brown", (byte) 25);
        ResponseEntity<String> postResponse = userApi.createUser(userPostDto, headers);
        String part1 = postResponse.getBody();
        log.debug("Part 1: {}", part1);

        User userUpdateDto = new User(3L, "Thomas", "Shelby", (byte) 25);
        ResponseEntity<String> updateResponse = userApi.updateUser(userUpdateDto, headers);
        String part2 = updateResponse.getBody();
        log.debug("Part 2 : {}", part2);

        ResponseEntity<String> deleteResponse = userApi.deleteUser(3L, headers);
        String part3 = deleteResponse.getBody();
        log.debug("Part 3: {}", part3);

        log.info("Result: {}{}{}", part1, part2, part3);
    }
}
