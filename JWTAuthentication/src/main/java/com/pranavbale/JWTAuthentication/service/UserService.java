package com.pranavbale.JWTAuthentication.service;

import com.pranavbale.JWTAuthentication.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private List<User> store =  new ArrayList<>();

    private void createUser() {
        store.add(new User(UUID.randomUUID().toString(), "pranav bale", "pranav@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "pratik jain", "pratik@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "prakesh kale", "prakesh@gmail.com"));

    }

    public List<User> getUser() {
        createUser();
        return store;
    }
}
