package com.huang.config;

import com.huang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 授权服务配置
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

    /**
     * 密码模式
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService);
    }

    /**
     * 授权码模式
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
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 授权类型
                // authorization_code 授权码模式
                // password 密码模式


                /**
                 * authorization_code：授权码类型，授权系统针对登录用户下发code，应用系统拿着code去授权系统换取token。
                 * implicit：隐式授权类型。authorization_code的简化类型，授权系统针对登录系统直接下发token，302 跳转到应用系统url。
                 * password：资源所有者（即用户）密码类型。应用系统采集到用户名密码，调用授权系统获取token。
                 * client_credentials：客户端凭据（客户端ID以及Key）类型。没有用户参与，应用系统单纯的使用授权系统分配的凭证访问授权系统。
                 * refresh_token：通过授权获得的刷新令牌 来获取 新的令牌。
                 */
                .authorizedGrantTypes("authorization_code","password");
    }
}
