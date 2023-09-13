package com.gmail.robertzylan.atiperaTask.dto;

import lombok.Getter;

@Getter
public class GithubBranchInfo {
    @Getter
    public class CommitBasicInfo {
        private String url;
        private String sha;
    }
    private String name;
    private CommitBasicInfo commit;
}
