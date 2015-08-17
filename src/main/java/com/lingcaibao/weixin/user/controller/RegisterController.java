package com.lingcaibao.weixin.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.lingcaibao.weixin.core.code.CommonStatus;
import com.lingcaibao.weixin.core.util.UUIDUtils;
import com.lingcaibao.weixin.user.entity.User;
import com.lingcaibao.weixin.user.service.UserService;
/**
 * 
 * @Title: RegisterController.java
 * @Description:(微信管理后台用户注册)
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年8月10日 下午12:06:42
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    private static Logger logger = LoggerFactory
            .getLogger(RegisterController.class);
    
    @Autowired
    private UserService userService;
   
    
    @Autowired
    private MemcachedClient memcachedClient;

    @RequestMapping(method = RequestMethod.GET)
    public String registerForm(HttpServletRequest request) {
    	WebUtils.setSessionAttribute(request, CommonStatus.KAPTCHA_SESSION_KEY.getKey(),  UUIDUtils.getUUID());
        return "account/register";
    }

    /**
     * 网站注册
     *
     * @param user
     * @param redirectAttributes
     * @param model
     * @param kaptcha
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String register(@Valid User user,
                           RedirectAttributes redirectAttributes, Model model,
                           @RequestParam(value = "kaptcha") String kaptcha,
                           HttpServletRequest request) {
        String code = (String) WebUtils.getSessionAttribute(request,CommonStatus.KAPTCHA_SESSION_KEY.getKey());

        //登录
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(user.getUsername());
        token.setPassword("".toCharArray());
        token.setRememberMe(true);
        currentUser.login(token);
        return "success";
    }

    /**
     * 手机版注册表单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mobile")
    public String registerFormByMobile(HttpServletRequest request, Model model) {
        String uuid = UUIDUtils.getUUID();
        WebUtils.setSessionAttribute(request,CommonStatus.KAPTCHA_SESSION_KEY.getKey(),uuid);
        model.addAttribute("kaptcha", uuid);
        return "account/register";
    }

    /**
     * 手机版注册
     *
     * @param user
     * @param redirectAttributes
     * @param model
     * @param kaptcha
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mobileReg")
    public String registerByMobile(@Valid User user, @RequestParam(value = "kaptcha") String kaptcha, 
    		HttpServletRequest request) {
    	String code=(String) WebUtils.getSessionAttribute(request,CommonStatus.KAPTCHA_SESSION_KEY.getKey());
    	
        //登录
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(user.getUsername());
        token.setPassword("".toCharArray());
        token.setRememberMe(true);
        currentUser.login(token);
        return "success";
    }


    /**
     * Ajax请求校验loginName是否唯一。
     */
    @RequestMapping(value = "checkUserName")
    @ResponseBody
    public String checkLoginName(@RequestParam("username") String username) {
        return null;
    }

    /**
     * Ajax请求校验验证码
     */
    @RequestMapping(value = "checkCaptcha")
    @ResponseBody
    public String checkCaptcha(@RequestParam("kaptcha") String kaptcha, HttpServletRequest request) {
    	String code=(String) WebUtils.getSessionAttribute(request, CommonStatus.KAPTCHA_SESSION_KEY.getKey());
        if (StringUtils.equalsIgnoreCase(kaptcha, code)) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Ajax请求校验email是否唯一
     * @param email
     * @return
     */
    @RequestMapping(value = "checkEmail")
    @ResponseBody
    public String checkEmail(@RequestParam("email") String email) {
    	return null;
    }

    /**
     * Ajax请求校验手机号是否唯一
     * @param mobile
     * @return
     */
    @RequestMapping(value = "checkMobile")
    @ResponseBody
    public String checkMobile(@RequestParam("mobile") String mobile) {
       return null;
    }

    /**
     * 忘记密码表单
     *
     * @return
     */
    @RequestMapping(value = "/findPassword", method = RequestMethod.GET)
    public String findPasswordForm() {
        return "account/findPassword";
    }
   

}
