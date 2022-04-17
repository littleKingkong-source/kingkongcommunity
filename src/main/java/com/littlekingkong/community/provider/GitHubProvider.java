package com.littlekingkong.community.provider;

import com.alibaba.fastjson.JSON;
import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.provider.dto.GitHubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import okhttp3.*;
import java.io.IOException;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 22:43*@since 1.0.0
 */
@Component
public class GitHubProvider {

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        System.out.println(accessTokenDTO);
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
           String string = response.body().string();
           //直接把string转为GitHubUser对象
           GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            System.out.println("githubUser=====" + gitHubUser);
            return gitHubUser;
        }catch (IOException e){
        }
        return null;
    }
}
