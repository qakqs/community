package life.majiang.community.Controller;

import javax.servlet.http.HttpServletRequest;


import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.provider.GithubProvider;

import java.util.UUID;

/**
 * AuthorizeConteoller
 */
@Controller
public class AuthorizeConteoller {
   @Autowired//spring 自动装箱
   private GithubProvider githubProvider;

   @Value("${github.client.id}") // 从配置文件里去读取文件的值赋到变量里面
   private String clientId;
   @Value("{github.client.secret}")
   private String clientSecret;
   @Value("{github.redirect.url}")
   private String redirectUrl;
   @Autowired
   UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null){
            //登录成功, 写 cookie 和 session
            request.getSession().setAttribute("user", githubUser);
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf( githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            return "redirect:/";
        }else{
            //登录失败,重新登录
        }
        return "redirect:/";
    }
}
