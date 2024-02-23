package com.gmail.robertzylan.atiperaTask.domain;

import com.gmail.robertzylan.atiperaTask.domain.githubApi.GithubRepositoryInfo;

import java.util.List;

public record ReturnRepositoryInfo(String repositoryName, String ownerLogin, List<ReturnBranchInfo> branches) {
    public ReturnRepositoryInfo(GithubRepositoryInfo githubRepositoryInfo, List<ReturnBranchInfo> branches) {
        this(githubRepositoryInfo.name(), githubRepositoryInfo.owner().login(), branches);
    }
}
