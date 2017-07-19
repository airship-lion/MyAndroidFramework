package study.toonan.com.myframework.util;

import java.util.List;

import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.widget.LoadingPage;

/**
 * Created by Administrator on 2017/2/7.
 */
public class ResultUtil {
    /**
     * 检查服务器返回的数据
     * @param result
     * @return
     */
    public static LoadingPage.LoadResult check(HttpResult result) {
        if (result == null || !result.isResult()) {
            return LoadingPage.LoadResult.ERROR;
        }  if (result.getResultList() != null && result.getResultList() instanceof List && (((List) result.getResultList()).isEmpty())) {
            return LoadingPage.LoadResult.EMPTY;
        }
        return LoadingPage.LoadResult.SUCCESS;
    }
}
