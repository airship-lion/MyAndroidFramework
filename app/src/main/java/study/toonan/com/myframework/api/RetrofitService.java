package study.toonan.com.myframework.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import study.toonan.com.myframework.bean.Bean;
import study.toonan.com.myframework.bean.DictBean;
import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.bean.RentPatrol;


public interface RetrofitService {

	@FormUrlEncoded
	@POST("appJzList.action")
	Call<HttpResult<List<Bean>>> getJzList(@FieldMap Map<String, String> map);

	@FormUrlEncoded
	@POST("appXtzdList.action")
	Call<HttpResult<List<DictBean>>> appXtzdList(@FieldMap Map<String, String> map);

	@FormUrlEncoded
	@POST("appRentPatrolList.action")
	Call<HttpResult<List<RentPatrol>>> appRentPatrolList(@FieldMap Map<String, String> map);

	@FormUrlEncoded
	@POST("appRentPatrolList.action")
	Observable<HttpResult<List<RentPatrol>>> appRentPatrolListObs(@FieldMap Map<String, String> map);

//	@FormUrlEncoded
	@POST("{url}")
	<T> Observable<HttpResult<T>> post(@Path("url") String url, @FieldMap Map<String, String> map);

}
