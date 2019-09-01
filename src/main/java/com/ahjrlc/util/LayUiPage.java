package com.ahjrlc.util;

/**
 * layui列表分页数据封装
 *
 * @author Administrator
 */
public class LayUiPage {

    /**
     * 反馈代码
     */
    private Integer code = 0;

    /**
     * 附加信息
     */
    private String msg;
    /**
     * 查询结果集总记录数
     */
    private Long count;
    /**
     * 查询结果集对象集合
     */
    private Object data;

    /**
     * 执行的分页大小
     */
    private Integer limit;

    public Integer getCode() {
        return code;
    }

    public LayUiPage setCode(int code) {
        this.code = code;
        return this;

    }

    public String getMsg() {
        return msg;
    }

    public LayUiPage setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public LayUiPage setCount(Long count) {
        this.count = count;
        return this;

    }

    public Object getData() {
        return data;
    }

    public LayUiPage setData(Object data) {
        this.data = data;
        return this;

    }

    public Integer getLimit() {
        return limit;
    }

    public LayUiPage setLimit(Integer limit) {
        this.limit = limit;
        return this;

    }


}
