package com.lingcaibao.weixin.core.tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.tagext.TagSupport;

import com.lingcaibao.weixin.core.util.CheckFileType;

public class FilterHTMLTag extends TagSupport {

    private static final long serialVersionUID = -3087821236259316254L;

    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    /**
     * 去掉所有的HTML标签和空白符号
     *
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 判断文件是否为图片
     *
     * @param str
     * @return
     */
    public static boolean isPic(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return CheckFileType.isPic(str);

    }

    /**
     * 判断是否有权限
     *
     * @param action
     * @param acl
     * @return
     */
    public static boolean hasPermission(int action, int acl) {
        boolean flag = false;
        if ((acl & action) == action) {
            // 判断角色优先级
            flag = !flag;
        }
        return flag;
    }

}
