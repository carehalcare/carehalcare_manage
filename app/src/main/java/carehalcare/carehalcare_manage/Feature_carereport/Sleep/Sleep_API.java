package carehalcare.carehalcare_manage.Feature_carereport.Sleep;

import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_texthist;
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

    @GET("sleepstates/{id}")
    Call<Sleep_text> getDataSleep_2(@Path("id") Long id);

    @GET("sleepstates/list/{userId}/{puserId}")
    Call<List<Sleep_text>> getDataSleep(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );

    @GET("sleepstatehists/list/{id}")
    Call<List<Sleep_texthist>> gethistSleep(
            @Path("id") Long id
    );

    //상세 조회
    @GET("sleepstatehists/{id}/{revNum}")
    Call<List<Sleep_texthist>> gethistSleep_detail(
            @Path("id") Long id,
            @Path("revNum") int revNum);
}
