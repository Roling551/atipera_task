package com.gmail.robertzylan.atiperaTask.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReturnRepositoryInfo {
    private String repositoryName;
    private String ownerLogin;
    private List<ReturnBranchInfo> branches;

    public ReturnRepositoryInfo(GithubRepositoryInfo githubRepositoryInfo, List<ReturnBranchInfo> branches) {
        this.repositoryName = githubRepositoryInfo.getName();
        this.ownerLogin = githubRepositoryInfo.getOwner().getLogin();
        this.branches = branches;
    }
}
