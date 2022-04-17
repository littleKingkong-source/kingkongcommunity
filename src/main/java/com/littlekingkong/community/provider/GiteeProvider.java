package com.littlekingkong.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.provider.dto.GiteeUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/25 20:50*@since 1.0.0
 */
@Service
@Slf4j
public class GiteeProvider {
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.uri}")
    private String redirectUri;


    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code&code=%s&client_id=%s&redirect_uri=%s&client_secret=%s";
        url = String.format(url, accessTokenDTO.getCode(), clientId, redirectUri, clientSecret);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            JSONObject jsonObject = JSON.parseObject(string);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }


    public GiteeUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            System.out.println("giteeUser======" + giteeUser);
            return giteeUser;
        } catch (Exception e) {
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }

}
