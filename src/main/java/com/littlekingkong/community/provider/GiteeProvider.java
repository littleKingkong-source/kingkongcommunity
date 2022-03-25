package com.littlekingkong.community.provider;

import com.alibaba.fastjson.JSON;
import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/25 20:50*@since 1.0.0
 */
@Component
public class GiteeProvider {
    //发起post请求获取token
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code="+accessTokenDTO.getCode()+
                        "&client_id="+accessTokenDTO.getClient_id()+"&redirect_uri="+accessTokenDTO.getRedirect_uri()+
                        "&client_secret="+accessTokenDTO.getClient_secret())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String str1 = string.split(":")[1];
            String str2 = str1.split("\"")[1];
            return str2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //发起get请求返回GitUser对象，
    public GiteeUser getGiteeUser(String token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user")
                .header("Authorization","token "+token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
//        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
//        Request request = new Request.Builder()
//                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code="+accessTokenDTO.getCode()+"&client_id="+accessTokenDTO.getClient_id()+"&redirect_uri="+accessTokenDTO.getRedirect_uri()+"&client_secret="+accessTokenDTO.getClient_secret())
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            String string = response.body().string();
//            System.out.println(string);
//            String str1 = string.split(":")[1];
//            String token = str1.split("\"")[1];
//            return token;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public GiteeUser getUser(String accessToken) {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://gitee.com/api/v5/user")
//                .header("Authorization","token "+accessToken)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            String string = response.body().string();
//            GiteeUser giteeUserDTO = JSON.parseObject(string, GiteeUser.class);
//            return giteeUserDTO;
//        } catch (IOException e) {
//        }
//        return null;
//    }

}
