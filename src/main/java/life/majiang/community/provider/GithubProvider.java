package life.majiang.community.provider;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;

@Component //仅仅把当前的类初始到spring的上下文
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=UTF-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                                     .url("https://github.com/login/oauth/access_token?client_id=f9fd86504232c51695ae&client_secret=efdc63983bcf6f59181eaaddf78f5d3f5c84b94e&code="+
                                            accessTokenDTO.get_Code() + "&redirect_uri=http://localhost:8080/callback")
                                     .post(body)
                                     .build();
        try (Response response = client.newCall(request).execute()){
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                                     .url("https://api.github.com/user?access_token=" + accessToken)
                                     .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
        }
        return null;
    }
}
