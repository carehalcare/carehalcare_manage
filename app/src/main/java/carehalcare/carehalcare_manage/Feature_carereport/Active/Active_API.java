package carehalcare.carehalcare_manage.Feature_carereport.Active;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Active_API {
       @Headers("Content-Type: application/json")

    @GET("activities/{id}")
    Call<Active_text> getDataActive_detail(@Path("id") Long id);

    @GET("activities/list/{uid}/{puid}")
    Call<List<Active_text>> getDataActive(
            @Path("uid") String userId,
            @Path("puid") String puserId
    );

    //변경 이력 리스트 조회
    @GET("activityhists/list/{id}")
    Call<List<Active_texthist>> gethistActive(
            @Path("id") Long id
    );

    //상세 조회
    @GET("activityhists/{id}/{revNum}")
    Call<List<Active_texthist>> gethistActive_detail(
            @Path("id") Long id,
            @Path("revNum") int revNum);
}
