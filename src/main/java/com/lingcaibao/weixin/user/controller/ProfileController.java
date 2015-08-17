package com.lingcaibao.weixin.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lingcaibao.weixin.user.entity.User;
import com.lingcaibao.weixin.user.security.ShiroDbRealm.ShiroUser;
import com.lingcaibao.weixin.user.service.UserService;
/**
 * 用户相关操作的Controller.
 */
@Controller
@RequestMapping(value = "/admin/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息--跳转至创建交易密码
     * @param model
     * @return
     */
    @RequestMapping(value = "findUserInfo", method = RequestMethod.GET)
    public String findUserInfo(Model model){
    	ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    	User user = userService.get(shiroUser.id);
    	model.addAttribute("user", user);
    	return "account/createPayPwd";
    }
    
    /**
     * 修改登陆密码
     * @param user
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "createPaypwd",method = RequestMethod.POST)
    public String createPaypwd(@Valid User user,HttpServletRequest request,Model model){
    	userService.update(user);
    	return "";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String updateForm(Model model, @RequestParam(value = "from", required = false) String from) {
        Long id = getCurrentUserId();
        model.addAttribute("user", userService.get(id));
        if (StringUtils.isNotEmpty(from)) {
            model.addAttribute("from", from);
        }
        return "account/profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@ModelAttribute("user") User user, Model model, @RequestParam(value = "from", required = false) String from) {
        Long id = getCurrentUserId();
        user.setId(id);
        /**
         * 此处不会修改用户名和密码，如需修改密码请走修改密码通道
         */
        userService.update(user);
        updateCurrentUserName(user.getRealName());
        model.addAttribute("success", "true");
        model.addAttribute("user", userService.get(id));
        if (StringUtils.isNotEmpty(from)) {
            return "redirect:" + from;
        }
        return "account/profile";
    }

    /**
     * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
     * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
     */
    @ModelAttribute
    public void getUser(
            @RequestParam(value = "id", defaultValue = "-1") Long id,
            Model model) {
        if (id != -1) {
            model.addAttribute("user", userService.get(id));
        }
    }

    /**
     * 取出Shiro中的当前用户Id.
     */
    private Long getCurrentUserId() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user.id;
    }

    /**
     * 更新Shiro中当前用户的用户名.
     */
    private void updateCurrentUserName(String realName) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        user.realName = realName;
    }
}
