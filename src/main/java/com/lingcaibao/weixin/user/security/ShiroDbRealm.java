package com.lingcaibao.weixin.user.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Objects;
import com.lingcaibao.weixin.core.util.Encodes;
import com.lingcaibao.weixin.user.entity.User;
import com.lingcaibao.weixin.user.service.UserService;
public class ShiroDbRealm extends AuthorizingRealm {

	
    private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
    
    @Autowired
    private UserService  userService;
    /**
     * 登录认证
     * <p/>
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = userService.findUserByLoginName(token.getUsername());
        if (user != null) {
            if (user.getFlag() == 1){
            	throw new LockedAccountException("账号 [" + user.getUsername()+ "] 已锁定.");
            }
            if (!(token.getUsername().equals("cairui")||token.getUsername().equals("lingcaiwx"))) {
            	throw new LockedAccountException("账号 [" + user.getUsername()+ "] 不能登录此系统.");
			}
            byte[] pwdSalt = Encodes.decodeHex(user.getPwdSalt());
            logger.info("----登录认证---:{}", user.getUsername());
            //更新用户登录次数
            userService.updateLogintimes(user);
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),user.getUsername(), user.getRealName()),
            		user.getPwd(),ByteSource.Util.bytes(pwdSalt), getName());
        } else {
        	return null;
        }
    }

    /**
     * 权限初始化
     * <p/>
     * <p/>
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
       // ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
               userService.HASH_ALGORITHM);
        matcher.setHashIterations(userService.HASH_INTERATIONS);
//
        setCredentialsMatcher(matcher);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public  class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String userName;
        public String realName;

        public ShiroUser(Long id, String userName, String realName) {
            this.id = id;
            this.userName = userName;
            this.realName = realName;
        }

        public String getRealName() {
            return realName;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return userName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(userName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (userName == null) {
                if (other.userName != null) {
                    return false;
                }
            } else if (!userName.equals(other.userName)) {
                return false;
            }
            return true;
        }
    }
}
