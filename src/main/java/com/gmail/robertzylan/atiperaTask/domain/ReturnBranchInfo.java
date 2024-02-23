package com.gmail.robertzylan.atiperaTask.domain;

import com.gmail.robertzylan.atiperaTask.domain.githubApi.GithubBranchInfo;

public record ReturnBranchInfo(String name, String lastCommitSha) {
    public ReturnBranchInfo(GithubBranchInfo githubBranchInfo) {
        this(githubBranchInfo.name(), githubBranchInfo.commit().sha());
    }
}
