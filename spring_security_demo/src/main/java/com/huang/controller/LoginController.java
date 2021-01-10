package com.huang.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

//    @Secured("ROLE_abc1") // 判断是否具有角色
//    @PreAuthorize("hasRole('ROLE_abc')")  // 允许以ROLE_开头
    @PostMapping("/login")
    public String login() {
        return "redirect:index.html";
    }
    @PostMapping("/toError")
    public String toError(){
        return "redirect:error.html";
    }

    @GetMapping("/demo")
    @ResponseBody
    public String demo() {
        return "Demo";
    }
}
