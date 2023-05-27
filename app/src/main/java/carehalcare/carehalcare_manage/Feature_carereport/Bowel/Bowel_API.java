package carehalcare.carehalcare_manage.Feature_carereport.Bowel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Bowel_API {

    @Headers("Content-Type: application/json")

    @GET("bowelmovements/{id}")
    Call<List<Bowel_text>> getDataBowel_detail(@Query("id") int id);

    @GET("bowelmovements/list/{userId}/{puserId}")
    Call<List<Bowel_text>> getDataBowel(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );

}
