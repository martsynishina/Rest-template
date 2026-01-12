package org.example.runner;

import lombok.RequiredArgsConstructor;
import org.example.service.UserServiceWork;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRunner implements CommandLineRunner {

    private final UserServiceWork userService;

    @Override
    public void run(String... args) {
        userService.runService();
    }
}
