package com.example.demo.service;

import com.example.demo.model.UserAccessDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GitHubService {

    @Value("${github.token}")
    private String token;

    @Value("${github.org}")
    private String org;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    public Map<String, UserAccessDTO> getUserRepoAccess() {

        Map<String, UserAccessDTO> result = new ConcurrentHashMap<>();

        String repoUrl = "https://api.github.com/orgs/" + org + "/repos?per_page=100";

        ResponseEntity<List<Map<String, Object>>> repoResponse = restTemplate.exchange(
                repoUrl,
                HttpMethod.GET,
                new HttpEntity<>(getHeaders()),
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> repos = repoResponse.getBody();
        if (repos == null) return result;

        repos.parallelStream().forEach(repo -> {

            String repoName = (String) repo.get("name");

            String collabUrl = "https://api.github.com/repos/" + org + "/" + repoName + "/collaborators";

            ResponseEntity<List<Map<String, Object>>> userResponse = restTemplate.exchange(
                    collabUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(getHeaders()),
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            List<Map<String, Object>> users = userResponse.getBody();
            if (users == null) return;

            for (Map<String, Object> user : users) {
                String username = (String) user.get("login");

                result.computeIfAbsent(username, UserAccessDTO::new)
                      .addRepo(repoName);
            }
        });

        return result;
    }
}