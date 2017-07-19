package study.toonan.com.myframework.net;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import study.toonan.com.myframework.api.RetrofitManager;
import study.toonan.com.myframework.bean.Bean;
import study.toonan.com.myframework.bean.DictBean;
import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.bean.RentPatrol;

/**
 * Created by Administrator on 2017/1/17.
 */
public class NetManager {
    public void getJzList(Map map,Callback<HttpResult<List<Bean>>> callback) {
        Call call = RetrofitManager.getInstance().retrofitService.getJzList(map);
        call.enqueue(callback);
	}

	public HttpResult<List<Bean>> getJzListSyn(Map map) {
		Call call = RetrofitManager.getInstance().retrofitService.getJzList(map);
        try {
            Response<HttpResult<List<Bean>>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

	public HttpResult<List<DictBean>> appXtzdListSyn(Map map) {
		Call call = RetrofitManager.getInstance().retrofitService.appXtzdList(map);
		try {
			Response<HttpResult<List<DictBean>>> response = call.execute();
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpResult<List<RentPatrol>> appRentPatrolListSyn(Map map) {
		Call call = RetrofitManager.getInstance().retrofitService.appRentPatrolList(map);
		try {
			Response<HttpResult<List<RentPatrol>>> response = call.execute();
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void  appRentPatrolList(Map map,Callback<HttpResult<List<RentPatrol>>> callback) {
		Call call = RetrofitManager.getInstance().retrofitService.appRentPatrolList(map);
		call.enqueue(callback);
	}

	public Observable<HttpResult<List<RentPatrol>>> appRentPatrolList(Map map) {
		return RetrofitManager.getInstance().retrofitService.appRentPatrolListObs(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	public Observable<HttpResult<List<RentPatrol>>> post(String url,Map map) {
		return RetrofitManager.getInstance().retrofitService.post(url,map).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
