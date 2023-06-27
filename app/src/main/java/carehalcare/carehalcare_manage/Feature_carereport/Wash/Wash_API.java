package carehalcare.carehalcare_manage.Feature_carereport.Wash;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Wash_API {
    @Headers("Content-Type: application/json")

    @GET("pcleanliness/{id}")
    Call<Wash_ResponseDTO> getDataWash_detail(@Query("id") Long id);

    @GET("pcleanliness/list/{userId}/{puserId}")
    Call<List<Wash_ResponseDTO>> getDataWash(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );
}
