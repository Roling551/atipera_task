package com.gmail.robertzylan.atiperaTask.domain.githubApi;

public record GithubRepositoryInfo(String name, boolean fork, OwnerInfo owner) {
    public record OwnerInfo(String login) {
    }
}
