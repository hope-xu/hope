package com.common;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class Result<T> {
    /**
     * 错误码
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;

    private PageInfo pageInfo;

    public String toString() {
        return "";
    }

    public Result(ResultEnum re) {
        this.code = String.valueOf(re.getCode());
        this.msg = re.getMsg();
    }

    public Result(HttpStatus hs) {
        this.code = String.valueOf(hs.value());
        this.msg = hs.getReasonPhrase();
    }

    public Result() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        if (null != data) map.put("data", data);
        if (null != pageInfo) map.put("pageInfo", pageInfo);
        return map;
    }

    public static <T> ResultBuilder<T> builder() {
        return new ResultBuilder<>();
    }

    public static class ResultBuilder<T> {
        private String code;
        private String msg;
        private T data;
        private PageInfo attr;

        public ResultBuilder<T> setCode(String code) {
            this.code = code;
            return this;
        }

        public ResultBuilder<T> setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResultBuilder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public Result<T> create() {
            Result<T> result = new Result<>();
            result.setCode(code);
            result.setMsg(msg);
            result.setData(data);
            result.setPageInfo(attr);
            return result;
        }

        public ResultBuilder<T> success() {
            this.code = "1";
            this.msg = "操作成功";
            return this;
        }

        public ResultBuilder<T> failed(String... message) {
            this.code = "0";
            this.msg = "操作失败";
            if (null != message && message.length > 0) this.msg = message[0];
            return this;
        }

        public ResultBuilder<T> setPageInfo(PageInfo pageInfo) {
            this.attr = pageInfo;
            return this;
        }
    }

    public static class PageInfo {
        public int page;
        public int totalPage;
        public long total;

        public PageInfo(int page, int totalPage, long total) {
            this.page = page;
            this.totalPage = totalPage;
            this.total = total;
        }
    }
}
