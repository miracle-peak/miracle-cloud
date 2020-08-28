package pers.miracle.miraclecloud.common.utils;


import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.miracle.miraclecloud.common.constant.GlobalConstant;

import java.util.Map;

/**
 * @author: 蔡奇峰
 * date: 2020/3/25 10:59
 * @Version V1.0
 */
public class R {

    /**
     * 数据对象
     */
    public static final String DATA_KEY = "data";

    private int code;
    private boolean success;
    private String message;
    private JSONObject data = new JSONObject();
    private Long total;
    private Long pageSize;
    private Long currentPage;
    // private Object data;
    // private ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();

    public static R ok() {
        R r = new R();
        r.setMessage("操作成功");
        r.setCode(GlobalConstant.SUCCESS);
        r.setSuccess(true);

        return r;
    }

    public static R ok(Object data) {
        R r = new R();
        r.setMessage("操作成功");
        r.setCode(GlobalConstant.SUCCESS);
        r.setSuccess(true);
        r.setData(data);

        return r;
    }

    /**
     * 响应结果包含分页信息
     *
     * @param page
     * @return
     */
    public static R ok(Page page) {
        R r = new R();
        r.setMessage("操作成功");
        r.setCode(GlobalConstant.SUCCESS);
        r.setSuccess(true);
        r.setData(page.getRecords());
        r.setTotal(page.getTotal());
        r.setPageSize(page.getSize());
        r.setCurrentPage(page.getCurrent());

        return r;
    }

    public static R error() {
        R r = new R();
        r.setMessage("操作失败");
        r.setCode(GlobalConstant.ERROR);
        r.setSuccess(false);

        return r;
    }


    public static R error(Object data) {
        R r = new R();
        r.setMessage("操作失败");
        r.setCode(GlobalConstant.ERROR);
        r.setSuccess(false);
        r.setData(data);

        return r;
    }


    // 链式编程=================

    public R success(Boolean success) {
        this.setSuccess(success);

        return this;
    }

    public R message(String msg) {
        this.setMessage(msg);

        return this;
    }

    public R code(int code) {
        this.setCode(code);

        return this;
    }

    public R data(JSONObject data) {
        this.data = data;

        return this;
    }

    public R data(Map<String, Object> data) {
        this.setData(data);

        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);

        return this;
    }

    public R() {
    }

    // 全局异常使用
    public R(int code, String msg) {
        this.setCode(code);

        this.setMessage(msg);
    }


    // setter getter

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data.put(DATA_KEY, data);
    }


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }
}
