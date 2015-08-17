/**
 * @Title: DateEditor.java
 * @Package com.palm.lingcai.util
 * @Description: TODO
 * @author jhe
 * @date 2013年12月8日 上午11:00:10
 * @version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */
package com.lingcaibao.weixin.core.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title:字符串转时间格式处理类，用于springmvc的绑定
 * @Description:
 * @Author jhe
 * @Date 2013年12月8日
 * @Version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */
public class DateEditor extends PropertyEditorSupport {
    private static final DateFormat DATEFORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final DateFormat TIMEFORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private DateFormat dateFormat;
    private boolean allowEmpty = true;

    public DateEditor() {
    }

    public DateEditor(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && StringUtils.isEmpty(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            try {
                if (this.dateFormat != null)
                    setValue(this.dateFormat.parse(text));
                else {
                    if (text.contains(":"))
                        setValue(TIMEFORMAT.parse(text));
                    else
                        setValue(DATEFORMAT.parse(text));
                }
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: "
                        + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        DateFormat dateFormat = this.dateFormat;
        if (dateFormat == null)
            dateFormat = TIMEFORMAT;
        return (value != null ? dateFormat.format(value) : "");
    }
}
