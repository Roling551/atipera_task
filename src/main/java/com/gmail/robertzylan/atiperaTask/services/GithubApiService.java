package com.gmail.robertzylan.atiperaTask.services;

import com.gmail.robertzylan.atiperaTask.dto.GithubBranchInfo;
import com.gmail.robertzylan.atiperaTask.dto.ReturnBranchInfo;
import com.gmail.robertzylan.atiperaTask.dto.ReturnRepositoryInfo;
import com.gmail.robertzylan.atiperaTask.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.gmail.robertzylan.atiperaTask.dto.GithubRepositoryInfo;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubApiService {
    @Value("${rest_template.repositories_url}")
    String repositoriesUrl;

    @Value("${rest_template.branches_url}")
    String branchesUrl;


    RestTemplate restTemplate;
    HttpEntity<String> entity;

    public GithubApiService(@Value("${github.token:}") String githubToken) {
        restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        if(githubToken != null && !githubToken.equals("")) {
            headers.set("Authorization", "token " + githubToken);
        }

        entity = new HttpEntity<>(headers);
    }

    public List<ReturnRepositoryInfo> getUsersRepositories(String userName) {
        List<GithubRepositoryInfo> repositories = Arrays.asList(getGithubRepositories(String.format(repositoriesUrl, userName), GithubRepositoryInfo[].class, entity));

        repositories = repositories.stream().filter(repository -> !repository.isFork()).collect(Collectors.toList());

        List<ReturnRepositoryInfo> resultList = repositories.parallelStream().map(
            repositoryInfo ->
                new ReturnRepositoryInfo(
                    repositoryInfo,
                    Arrays.asList(getObjectFromUrl(String.format(branchesUrl, userName, repositoryInfo.getName()), GithubBranchInfo[].class, entity))
                    .stream().map(branchInfo ->
                        new ReturnBranchInfo(branchInfo)
                    ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());

        return resultList;
    }

    private <T> T getGithubRepositories(String address, Class<T> responseType, HttpEntity<String> entity) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(address, HttpMethod.GET, entity, responseType);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User could not be found");
            } else {
                throw new RuntimeException();
            }
        } catch(HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User could not be found");
            }
            throw new RuntimeException();
        }
    }

    private <T> T getObjectFromUrl(String address, Class<T> responseType, HttpEntity<String> entity) {
        ResponseEntity<T> response = restTemplate.exchange(address, HttpMethod.GET, entity, responseType);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException();
        }
    }
}
