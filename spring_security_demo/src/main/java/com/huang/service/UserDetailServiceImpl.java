package com.huang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名去数据库查询，如果不存在抛出异常 UsernameNotFoundException
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        String password = passwordEncoder.encode("123");
        // 比较密码
        // admin user 是两个权限
        // ROLE_abc 是abc角色，必须带ROLE_前缀
        return new  User(username,password, AuthorityUtils
                .commaSeparatedStringToAuthorityList("admin,user,ROLE_abc,/index.html," +
                        "/insert,/delete"));
    }
}
