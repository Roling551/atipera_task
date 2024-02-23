package com.gmail.robertzylan.atiperaTask.controllers;

import com.gmail.robertzylan.atiperaTask.domain.ReturnRepositoryInfo;
import com.gmail.robertzylan.atiperaTask.services.GithubApiService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RepositoryInfoController {

    private GithubApiService githubApiService;

    public RepositoryInfoController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @RequestMapping("/repositories/{userName}")
    public List<ReturnRepositoryInfo> getRepositoriesOfUser(@PathVariable() String userName) {
        return githubApiService.getUsersRepositories(userName);
    }
}
