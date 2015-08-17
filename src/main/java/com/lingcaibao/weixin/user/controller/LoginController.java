package com.lingcaibao.weixin.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lingcaibao.weixin.user.security.ShiroDbRealm.ShiroUser;
import com.lingcaibao.weixin.user.service.UserService;

/**
 * @Title: LoginController.java
 * @Description:(LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，真正登录的POST请求由Filter完成,)
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年8月10日 下午12:07:12
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "account/login";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
            Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,userName);
        return "account/login";
    }
    
    /**
     * 检查用户是否登录
     *
     * @return 用户名
     */
    @RequestMapping(value = "check")
    @ResponseBody
    public String check() {
        Subject subject = SecurityUtils.getSubject();
        String loginName = "";
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        if (null != user) {
            loginName = user.userName;
        }
        return loginName;
    }

    /**
     * ajax登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "ajax")
    @ResponseBody
    public String ajaxLogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        //登录
        String result = "false";
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(username);
        token.setPassword(password.toCharArray());
        token.setRememberMe(true);
        try {
            currentUser.login(token);
            result = "true";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
