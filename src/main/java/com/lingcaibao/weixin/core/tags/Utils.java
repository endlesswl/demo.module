package com.lingcaibao.weixin.core.tags;

/**
 * @author badqiu
 */
class Utils {

    public static String BLOCK = "__jsp_override__";

    static String getOverrideVariableName(String name) {
        return BLOCK + name;
    }

}
