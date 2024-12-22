package com.cryptochenger;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class HomeController {
    private final TaskRepo taskRepo;

    @GetMapping("/test")
    public String function() {
        return taskRepo.save(
                Task.builder()
                        .title(String.valueOf(new Random().nextInt(10000)))
                        .description("its first task lololo")
                        .build()
        ).getTitle();
    }

    @GetMapping("/{title}")
    public Task getTask(String title) {
        Optional<Task> task = taskRepo.findByTitle(title);
        if (task.isPresent()) {
            return task.get();
        }

        return null;
    }
}
