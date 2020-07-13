package cn.zxj.utils.http;

import org.apache.commons.lang.builder.ToStringBuilder;

public class HttpClientResult {
    /** 接口调用是否成功*/
    private boolean success;
    /** Http接口返回的数据 */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

