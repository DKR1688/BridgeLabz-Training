package com.mygreetingsapp.controller;

import com.mygreetingsapp.model.Greeting;
import com.mygreetingsapp.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping
    public List<Greeting> listGreetings() {
        return greetingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Greeting> getGreeting(@PathVariable int id) {
        Greeting greeting = greetingService.findById(id);
        return greeting != null ? ResponseEntity.ok(greeting) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
        if (greeting == null || greeting.getMessage() == null || greeting.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Greeting created = greetingService.create(greeting.getMessage().trim());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable int id, @RequestBody Greeting greeting) {
        if (greeting == null || greeting.getMessage() == null || greeting.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        greeting.setId(id);
        return greetingService.update(greeting) ? ResponseEntity.ok(greeting) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable int id) {
        return greetingService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
