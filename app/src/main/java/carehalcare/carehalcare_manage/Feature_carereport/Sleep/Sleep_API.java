package carehalcare.carehalcare_manage.Feature_carereport.Sleep;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Sleep_API {
    String URL = "http://172.20.4.180:8080/";
    @Headers("Content-Type: application/json")
    @POST("sleepstates")
    Call<List<Sleep_text>> postDataSleep(
            @Body Sleep_text sleep_text
    );
    @GET("sleepstates/{id}")
    Call<Sleep_text> getDataSleep_2(@Query("id") Long id);

    @GET("sleepstates")
    Call<List<Sleep_text>> getDataSleep_3();

    @GET("sleepstates/list/{userId}/{puserId}")
    Call<List<Sleep_text>> getDataSleep(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );

    @DELETE("sleepstates/{id}")
    Call<Void> deleteDataSleep(@Path("id") Long id);
}
