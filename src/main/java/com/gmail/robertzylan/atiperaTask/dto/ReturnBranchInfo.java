package com.gmail.robertzylan.atiperaTask.dto;

import lombok.Getter;

@Getter
public class ReturnBranchInfo {
    private String name;
    private String lastCommitSha;

    public ReturnBranchInfo(GithubBranchInfo githubBranchInfo) {
        this.name = githubBranchInfo.getName();
        this.lastCommitSha = githubBranchInfo.getCommit().getSha();
    }
}
