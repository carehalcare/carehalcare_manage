package carehalcare.carehalcare_manage.Feature_carereport.Active;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Active_API {
    String URL = "http://192.168.0.18:8080/";

    @Headers("Content-Type: application/json")

    @GET("activities/{id}")
    Call<List<Active_text>> getDataActive_detail(@Query("id") int id);

    @GET("activities/list/{userId}/{puserId}")
    Call<List<Active_text>> getDataActive(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );
}
