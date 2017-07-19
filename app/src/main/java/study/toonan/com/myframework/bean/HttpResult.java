package study.toonan.com.myframework.bean;

/**
 * Created by Administrator on 2017/1/17.
 */
public class HttpResult<T> {

    /**
     * msg : 查询成功
     * result : true
     * resultList : null
     */

    private String msg;
    private boolean result;
    private T resultList;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private int totalPages;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getResultList() {
        return resultList;
    }

    public void setResultList(T resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "msg='" + msg + '\'' +
                ", result=" + result +
                ", resultList=" + resultList +
                '}';
    }
}
