package com.gmail.robertzylan.atiperaTask.dto;

import lombok.Getter;

@Getter
public class GithubRepositoryInfo {
    @Getter
    public class OwnerInfo {
        private String login;
    }
    private String name;
    private String commits_url;
    private boolean fork;
    private OwnerInfo owner;
}
