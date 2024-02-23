package com.gmail.robertzylan.atiperaTask;

import com.gmail.robertzylan.atiperaTask.domain.ReturnRepositoryInfo;
import com.gmail.robertzylan.atiperaTask.domain.githubApi.GithubBranchInfo;
import com.gmail.robertzylan.atiperaTask.domain.githubApi.GithubRepositoryInfo;
import com.gmail.robertzylan.atiperaTask.services.GithubApiResponseErrorHandler;
import com.gmail.robertzylan.atiperaTask.services.GithubApiService;
import com.gmail.robertzylan.atiperaTask.utility.ListTransformer;
import com.gmail.robertzylan.atiperaTask.utility.ParallelStreamListTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GithubApiServiceTest {

    @Mock
    GithubApiResponseErrorHandler githubApiResponseErrorHandler;
    @Spy
    ListTransformer listTransformer = new ParallelStreamListTransformer();
    @Mock
    RestTemplate restTemplate;


    @Test
    public void getRepositoriesTest() {
        GithubApiService githubApiService = new GithubApiService(
                githubApiResponseErrorHandler,
                listTransformer,
                restTemplate,
                "url_repositories_%s",
                "url_branches_%s_%s",
                "token"
        );

        GithubRepositoryInfo[] repositoriesFromApi = new GithubRepositoryInfo[1];
        repositoriesFromApi[0] = new GithubRepositoryInfo("test_name", false, new GithubRepositoryInfo.OwnerInfo("test_login"));
        when(restTemplate.exchange(
                eq("url_repositories_test_user"),
                any(),
                any(),
                any(Class.class)
        )).thenReturn(new ResponseEntity(repositoriesFromApi, HttpStatus.ACCEPTED));

        GithubBranchInfo[] branches_from_api = new GithubBranchInfo[1];
        branches_from_api[0] = new GithubBranchInfo("test_branch_name", new GithubBranchInfo.CommitBasicInfo("test_sha"));
        when(restTemplate.exchange(
                eq("url_branches_test_user_test_name"),
                any(),
                any(),
                any(Class.class)
        )).thenReturn(new ResponseEntity(branches_from_api, HttpStatus.ACCEPTED));

        List<ReturnRepositoryInfo> userRepositories = githubApiService.getUsersRepositories("test_user");

        InOrder inOrder = inOrder(restTemplate);

        inOrder.verify(restTemplate).exchange(
                eq("url_repositories_test_user"),
                any(),
                any(),
                any(Class.class)
        );

        inOrder.verify(restTemplate).exchange(
                eq("url_branches_test_user_test_name"),
                any(),
                any(),
                any(Class.class)
        );

        assertEquals(1, userRepositories.size());
        assertEquals("test_name", userRepositories.get(0).repositoryName());
        assertEquals("test_login", userRepositories.get(0).ownerLogin());
        assertEquals(1, userRepositories.get(0).branches().size());
        assertEquals("test_branch_name", userRepositories.get(0).branches().get(0).name());
        assertEquals("test_sha", userRepositories.get(0).branches().get(0).lastCommitSha());
    }

    @Test
    public void getForksTest() {
        GithubApiService githubApiService = new GithubApiService(
                githubApiResponseErrorHandler,
                listTransformer,
                restTemplate,
                "url_repositories_%s",
                "url_branches_%s_%s",
                "token"
        );

        GithubRepositoryInfo[] repositoriesFromApi = new GithubRepositoryInfo[1];
        repositoriesFromApi[0] = new GithubRepositoryInfo("test_name", true, new GithubRepositoryInfo.OwnerInfo("test_login"));
        when(restTemplate.exchange(
                eq("url_repositories_test_user"),
                any(),
                any(),
                any(Class.class)
        )).thenReturn(new ResponseEntity(repositoriesFromApi, HttpStatus.ACCEPTED));

        List<ReturnRepositoryInfo> userRepositories = githubApiService.getUsersRepositories("test_user");

        InOrder inOrder = inOrder(restTemplate);

        inOrder.verify(restTemplate).exchange(
                eq("url_repositories_test_user"),
                any(),
                any(),
                any(Class.class)
        );

        inOrder.verify(restTemplate, never()).exchange(
                eq("url_branches_test_user_test_name"),
                any(),
                any(),
                any(Class.class)
        );
    }
}
