package com.huang.config;

import com.huang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.support.collections.RedisStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权认证服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

//    @Autowired
//    private TokenStore tokenStore;

    /**
     * 密码模式
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置jwt增强内容
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService);

        // 将token存储在redis中
//        endpoints.tokenStore(tokenStore );

        //使用spring security oauth 生成 jwt
        endpoints.tokenStore(tokenStore)
                // accessToken 转成 jwt
                .accessTokenConverter(jwtAccessTokenConverter)
                // 添加jwt增强内容
                .tokenEnhancer(chain);


    }

    /**
     * 授权码模式
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端id
                .withClient("client")
                // 密钥
                .secret(passwordEncoder.encode("112233"))
                // 重定向地址
//                .redirectUris("http://www.baidu.com")
                .redirectUris("http://localhost:8081/login")
                // 授权范围
                .scopes("all")
                // accessToken 失效时间
                .accessTokenValiditySeconds(60)
                // 刷新令牌过期时间
                .refreshTokenValiditySeconds(8000)
                // 自动运行授权
                .autoApprove(true)



                // 授权类型
                // authorization_code 授权码模式
                // password 密码模式
                // refresh_token 刷新令牌
                /**
                 * authorization_code：授权码类型，授权系统针对登录用户下发code，应用系统拿着code去授权系统换取token。
                 * implicit：隐式授权类型。authorization_code的简化类型，授权系统针对登录系统直接下发token，302 跳转到应用系统url。
                 * password：资源所有者（即用户）密码类型。应用系统采集到用户名密码，调用授权系统获取token。
                 * client_credentials：客户端凭据（客户端ID以及Key）类型。没有用户参与，应用系统单纯的使用授权系统分配的凭证访问授权系统。
                 * refresh_token：通过授权获得的刷新令牌 来获取 新的令牌。
                 */
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 获取密钥  配置单点登录的必要配置
        security.tokenKeyAccess("isAuthenticated()");
    }
}
