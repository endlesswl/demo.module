package com.lingcaibao.weixin.core.exceptions;

/**
 *上传相关异常
 */
public class ImportException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * message key
     */
    private String code;

    /**
     * message params
     */
    private Object[] values;

    public ImportException() {
    }

    public ImportException(String s) {
        super(s);
    }

    /**
     * 
     * @param s 异常内容
     * @param code 异常编码
     */
    public ImportException(String s, String code) {
        super(s);
        this.code = code;
    }

    public ImportException(String s, Throwable throwable, String code, Object[] values) {
        super(s, throwable);
        this.code = code;
        this.values = values;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
