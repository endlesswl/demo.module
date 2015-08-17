package com.lingcaibao.weixin.core.tags;

import javax.servlet.jsp.tagext.TagSupport;
import java.lang.reflect.Method;

/**
 * 根据传入的枚举类取得中文返回值
 * Created by jianhe on 14-2-24.
 */
public class EnumValueTag extends TagSupport {

    private static final long serialVersionUID = -1L;

    private static final String preClassName = "com.palm.lingcai.statuscode.";

    /**
     * 根据原生int型参数取值
     *
     * @param className
     * @param value
     * @return
     */
    public static String getIntName(String className, int value) {
        try {
            Class cls = Class.forName(preClassName + className);
            Method putMethod = cls.getMethod("getName", new Class[]{Integer.TYPE});
            String result = (String) putMethod.invoke(cls, value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 根据String型参数取值
     *
     * @param className
     * @param value
     * @return
     */
    public static String getStringName(String className, String value) {
        try {
            Class cls = Class.forName(preClassName + className);
            Method putMethod = cls.getMethod("getName", new Class[]{String.class});
            String result = (String) putMethod.invoke(cls, value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
