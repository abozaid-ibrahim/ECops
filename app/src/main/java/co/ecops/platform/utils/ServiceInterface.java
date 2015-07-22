package co.ecops.platform.utils;

import java.util.Map;

import co.ecops.platform.model.Post;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by ZEID on 7/14/2015.
 */
public interface ServiceInterface {
    @GET("/v3/search/feeds")
    void searchFeeds(@QueryMap Map<String, String> options, Callback<RetrofitResponse> cb);

    @GET("/service?action=phcall&phid="+Utils.APP_ID)
    void message(Callback<Post> cb);
}
