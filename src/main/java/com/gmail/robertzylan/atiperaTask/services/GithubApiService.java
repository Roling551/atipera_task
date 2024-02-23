package com.gmail.robertzylan.atiperaTask.services;

import com.gmail.robertzylan.atiperaTask.domain.ReturnBranchInfo;
import com.gmail.robertzylan.atiperaTask.domain.ReturnRepositoryInfo;
import com.gmail.robertzylan.atiperaTask.domain.githubApi.GithubBranchInfo;
import com.gmail.robertzylan.atiperaTask.domain.githubApi.GithubRepositoryInfo;
import com.gmail.robertzylan.atiperaTask.utility.ListTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubApiService {
    private final String repositoriesUrl;
    private final String branchesUrl;
    private final RestTemplate restTemplate;
    private final HttpEntity<String> entity;
    private final ListTransformer listTransformer;

    public GithubApiService(
            GithubApiResponseErrorHandler githubApiResponseErrorHandler,
            ListTransformer listTransformer,
            RestTemplate restTemplate,
            @Value("${rest_template.repositories_url}")
            String repositoriesUrl,
            @Value("${rest_template.branches_url}")
            String branchesUrl,
            @Value("${github.token:}")
            String githubToken
    ) {
        this.listTransformer = listTransformer;
        this.restTemplate = restTemplate;
        this.repositoriesUrl = repositoriesUrl;
        this.branchesUrl = branchesUrl;

        restTemplate.setErrorHandler(githubApiResponseErrorHandler);

        HttpHeaders headers = new HttpHeaders();
        if (githubToken != null && !githubToken.isEmpty()) {
            headers.set("Authorization", "token " + githubToken);
        }
        entity = new HttpEntity<>(headers);
    }

    public List<ReturnRepositoryInfo> getUsersRepositories(String userName) {
        List<GithubRepositoryInfo> repositories =
                Arrays.stream(
                                restTemplate.exchange(
                                        String.format(repositoriesUrl, userName),
                                        HttpMethod.GET,
                                        entity,
                                        GithubRepositoryInfo[].class).getBody()
                        )
                        .filter(repository -> !repository.fork()).toList();
        return listTransformer.transform(repositories,
                repositoryInfo ->
                        new ReturnRepositoryInfo(
                                repositoryInfo,
                                Arrays.stream(
                                                restTemplate.exchange(
                                                        String.format(branchesUrl, userName, repositoryInfo.name()),
                                                        HttpMethod.GET,
                                                        entity,
                                                        GithubBranchInfo[].class).getBody())
                                        .map(ReturnBranchInfo::new)
                                        .collect(Collectors.toList())
                        )
        );
    }
}
