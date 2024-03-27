package com.example.demo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class GithubOAuth2UserService extends DefaultOAuth2UserService {

    Logger logger = LoggerFactory.getLogger(GithubOAuth2UserService.class);

    GitHubService gitHubService;

    public GithubOAuth2UserService(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    //https://dev.to/relive27/spring-security-oauth2-login-51lj
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();

        // Retrieve the access token
        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        // Make a request to the GitHub API to fetch the email address
        List<Email> result = gitHubService.getEmails(accessToken);

        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setUserId(String.valueOf(attributes.get("id")));
        gitHubUser.setName((String) attributes.get("name"));
        gitHubUser.setUrl((String) attributes.get("html_url"));
        gitHubUser.setAvatarUrl((String) attributes.get("avatar_url"));
        gitHubUser.setLogin((String) attributes.get("login"));
        gitHubUser.setEmail(result.getFirst().email());
        updateUser(gitHubUser);

        return oidcUser;
    }



    private void updateUser(GitHubUser gitUser) {
        logger.info("User detected, {}, {}", gitUser.getLogin(), gitUser.getName());
//        var user = userInfoRepository.findByUserId(gitUser.getUserId());
//        if (user == null) {
//            logger.info("New user detected, {}, {}", gitUser.getLogin(), gitUser.getName());
//            user = new UserInfo();
//        }
//
//        user.setUserId(gitUser.getUserId());
//        user.setName(gitUser.getName());
//        user.setUrl(gitUser.getUrl());
//        user.setLogin(gitUser.getLogin());
//        user.setAvatarUrl(gitUser.getAvatarUrl());
//
//        userInfoRepository.save(user);
    }
}
