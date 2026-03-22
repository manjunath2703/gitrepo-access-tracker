package com.example.demo.controller;

import com.example.demo.model.UserAccessDTO;
import com.example.demo.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    @Autowired
    private GitHubService service;

    @GetMapping("/access")
    public Map<String, UserAccessDTO> getAccess() {
        return service.getUserRepoAccess();
    }
}