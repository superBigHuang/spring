package com.huang.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authorization, HttpServletRequest request){
        // 重构，将方法改为jwt令牌解析
        String header = request.getHeader("Authorization");
        System.out.println(header);
        // 截取掉bearer和后面的一个空格
        String token = header.substring(header.lastIndexOf("bearer") + 7);
        return Jwts.parser()
                // 如果密钥是中文的转成u8防止乱码
                .setSigningKey("huang".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();

//        return authorization.getPrincipal();
    }
}
