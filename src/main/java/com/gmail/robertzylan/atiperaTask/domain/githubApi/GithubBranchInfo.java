package com.gmail.robertzylan.atiperaTask.domain.githubApi;

public record GithubBranchInfo(String name, CommitBasicInfo commit) {
    public record CommitBasicInfo(String sha) {
    }
}
