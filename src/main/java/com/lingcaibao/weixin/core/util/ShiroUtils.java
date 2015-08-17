/**
 *
 */
package com.lingcaibao.weixin.core.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.lingcaibao.weixin.user.security.ShiroDbRealm.ShiroUser;

import java.util.ArrayList;
import java.util.List;


/**
 * 字符串工具类
 *
 * @author jhe
 */
public class ShiroUtils {

    /**
     * 商家用户权限
     */
    public static final String BUSINESS = "business";

    /**
     * 普通用户权限
     */
    public static final String USER = "user";
    /**
     * 管理员权限
     */
    public static final String ADMINISTRATOR = "administrator";

    /**
     * 权限字符串合并
     * <p/>
     * 如：user:*:*和*:read:*合并后为user:read:*
     *
     * @param str
     * @param args
     * @return
     */
    public static String format(String str, String str2) {

        // 这里用于验证数据有效性
        if (str == null || "".equals(str))
            return "";

        // 这里的作用是只匹配{}里面是数字的子字符串
        String[] strArray = str.split(":");
        String[] str2Array = str2.split(":");
        strArray[1] = str2Array[1];
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String astr : strArray) {
            if (flag) {
                result.append(":");
            } else {
                flag = true;
            }
            result.append(astr);
        }
        return result.toString();
    }

    /**
     * 短网址生成工具
     *
     * @param url
     * @return
     */
    public static String shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "solohui";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};
        // 对传入网址进行 MD5 加密
//        String sMD5EncryptResult = CMyEncrypt.md5(key + url);
//        String hex = sMD5EncryptResult;
        String hex = "";
        String[] resUrl = new String[1];
        for (int i = 0; i < 1; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
            // long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";

            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }

            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }

        return resUrl[0];
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean hasContainAll = hasContainsAll("acd", "cdab");
        System.out.println(hasContainAll);
        // 打印出结果
        System.out.println(format("user:*:*", "*:read:*"));
    }

    /**
     * 获取当前用户 shiroUser
     *
     * @return shiroUser
     */
    public static ShiroUser getCurrentUser() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * 判断当前用户是否有该角色
     *
     * @param role 角色名称
     * @return true ,拥有;
     * false,没有
     */
    public static boolean hasRole(String role) {
        Subject currentUser = SecurityUtils.getSubject();
        boolean hasRole = currentUser.hasRole(role);
        return hasRole;
    }

    /**
     * 判断当前用户是否有read 权限
     *
     * @return true, 拥有 ;
     * false ,没有
     */
    public static boolean hasReadPermission() {
        return SecurityUtils.getSubject().isPermitted("permission:read:*");
    }

    /**
     * 判断当前用户是否有create 权限
     *
     * @return true, 拥有 ;
     * false ,没有
     */
    public static boolean hasCreatePermission() {
        return SecurityUtils.getSubject().isPermitted("permission:create:*");
    }

    /**
     * 判断当前用户是否有update 权限
     *
     * @return true, 拥有 ;
     * false ,没有
     */
    public static boolean hasUpdatePermission() {
        return SecurityUtils.getSubject().isPermitted("permission:update:*");
    }

    /**
     * 判断当前用户是否有delete 权限
     *
     * @return true, 拥有 ;
     * false ,没有
     */
    public static boolean hasDeletePermission() {
        return SecurityUtils.getSubject().isPermitted("permission:delete:*");
    }

    /**
     * 判断当前用户是否有Owner 权限
     *
     * @return true, 拥有 ;
     * false ,没有
     */
    public static boolean hasOwnerPermission() {
        return SecurityUtils.getSubject().isPermitted("permission:owner:*");
    }

    /**
     * 判断字符串是否含有另一的字符串
     * hasContainAll("abc","cb")返回true;
     * hasContainAll("abc","ab")返回true;
     * hasContainAll("abc","bd")返回false;
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean hasContainsAll(String str1, String str2) {
        if (str1 == null || str1.equals("")) {
            return false;
        }
        if (str2 == null || str2.equals("")) {
            return false;
        }
        int beginIndex = 0;
        int endIndex = 1;
        int length = str2.length();
        List<Boolean> list = new ArrayList<Boolean>();
        for (int i = 0; i < length; i++) {
            String str = str2.substring(beginIndex + i, endIndex + i);
            System.out.println(str);
            if (str1.contains(str)) {
                list.add(true);
            } else {
                list.add(false);
            }
        }
        return list.contains(false) == true ? false : true;
    }

}
