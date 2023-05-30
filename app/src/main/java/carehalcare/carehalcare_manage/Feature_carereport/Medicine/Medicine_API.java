package carehalcare.carehalcare_manage.Feature_carereport.Medicine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Medicine_API {

    @Headers("Content-Type: application/json")

    @GET("administrations/{id}")
    Call<List<Medicine_text>> getDatamedicine_detail(@Query("id") int id);

    @GET("administrations/list/{userId}/{puserId}")
    Call<List<Medicine_text>> getDatamedicine(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );
}
