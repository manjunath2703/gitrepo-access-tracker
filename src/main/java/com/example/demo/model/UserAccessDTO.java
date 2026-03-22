package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class UserAccessDTO {

    private String username;
    private List<String> repositories;

    public UserAccessDTO(String username) {
        this.username = username;
        this.repositories = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRepositories() {
        return repositories;
    }

    public void addRepo(String repo) {
        this.repositories.add(repo);
    }
}