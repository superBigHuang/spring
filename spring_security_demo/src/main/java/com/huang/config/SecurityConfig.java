package com.huang.config;

import com.huang.handler.MyAccessDeniedHandler;
import com.huang.handler.MyAuthenticationFailureHandler;
import com.huang.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 自定义登录页面
                .loginPage("/login.html")
                // 必须和表但提交的接口一样，会去执行自定义登录逻辑
                .loginProcessingUrl("/login")
                // 登录成功后跳转的请求，必须为post
                .successForwardUrl("/login")
                // 登录失败跳转的请求，必须post请求
                .failureForwardUrl("/toError")
                // 自己实现重定向到百度，自定义登录处理器
//                .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))
                // 自定义登录失败处理器
//                .failureHandler(new MyAuthenticationFailureHandler("/error.html"))
                // 用户名name
                .usernameParameter("username123")
                // 密码name
                .passwordParameter("password123");

        // 授权
        http.authorizeRequests()
                /**
                 * ant表达式，用于匹配URL规则
                 * ? 匹配一个字符
                 * * 匹配0个或多个字符
                 * ** 匹配0个或多个目录
                 * ************************************************************
                 * 放行js目录下的所有脚本文件  antMatchers("/js/**").permitAll()
                 * ************************************************************
                 * 放行所有js文文件   antMatchers("/**\/*.js").permitAll()
                 */
                // 放行login.html，不需要认证
//                .antMatchers("/login.html").permitAll()
                // 使用access
                .antMatchers("/login.html").access("permitAll")
                // 放行error.html, 不需要认证
                .antMatchers("/error.html").permitAll()
                // 放行静态文件
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                // 放行所有jpg文件
//                .antMatchers("/**/*.jpg").permitAll()
//                .antMatchers("/**/*.mp4").permitAll()
//                .antMatchers("/vedio.html").permitAll()
                // 使用正则放行jpg文件
//                .regexMatchers(".+[.]jpg").permitAll()
//                .regexMatchers("/aaa/demo").permitAll()
                // 如果项目带有servlet path的话使用
//                .mvcMatchers("/demo").servletPath("/aaa").permitAll()


                // 权限控制  如果报403表示权限不够
                // 基于权限
//                .antMatchers("/main.html").hasAuthority("admin1")
                // 使用access
//                .antMatchers("/main.html").access("hasAuthority('admin1')")
                // 多个权限，
//                .antMatchers("/main.html").hasAnyAuthority("admin","root")
                // 基于角色判断。hasRole()方法内不需要加ROLE_
//                .antMatchers("/main.html").hasRole("abc")
                // 多个角色
//                .antMatchers("/main.html").hasAnyRole("ABC","abc")
                // 基于ip地址
//                .antMatchers("/main.html").hasIpAddress("127.0.0.1")

                // 所有的请求都必须认证才能访问，必须登录
                // 不能放在最上面，spring security从上往下执行
//                .anyRequest().authenticated();
                // 自定义access方法
                .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");
        // 异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        // 关闭csrf
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder getPw() {
        return new BCryptPasswordEncoder();
    }
}
