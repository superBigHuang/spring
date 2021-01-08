package com.huang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 自定义登录页面
                .loginPage("/login.html")
                // 必须和表但提交的接口一样，会去执行自定义登录逻辑
                .loginProcessingUrl("/login")
                // 登录成功后跳转的请求，必须为post
                .successForwardUrl("/login");

        // 授权
        http.authorizeRequests()
                // 放行login.html，不需要认证
                .antMatchers("/login.html").permitAll()
                // 所有的请求都必须认证才能访问，必须登录
                .anyRequest().authenticated();

        // 关闭csrf
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }
}
