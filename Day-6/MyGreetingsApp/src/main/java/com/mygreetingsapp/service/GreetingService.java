package com.mygreetingsapp.service;

import com.mygreetingsapp.model.Greeting;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GreetingService {

    private final Map<Integer, Greeting> greetingStore = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    public GreetingService() {
        create("Hello, welcome to MyGreetingsApp!");
        create("Hi there, enjoy the Spring MVC greeting app.");
    }

    public List<Greeting> findAll() {
        List<Greeting> greetings = new ArrayList<>(greetingStore.values());
        Collections.sort(greetings, (a, b) -> Integer.compare(a.getId(), b.getId()));
        return greetings;
    }

    public Greeting findById(int id) {
        return greetingStore.get(id);
    }

    public Greeting create(String message) {
        int id = idGenerator.incrementAndGet();
        Greeting greeting = new Greeting(id, message);
        greetingStore.put(id, greeting);
        return greeting;
    }

    public boolean update(Greeting greeting) {
        if (greeting == null || greeting.getId() <= 0 || !greetingStore.containsKey(greeting.getId())) {
            return false;
        }
        greetingStore.put(greeting.getId(), greeting);
        return true;
    }

    public boolean delete(int id) {
        return greetingStore.remove(id) != null;
    }
}
