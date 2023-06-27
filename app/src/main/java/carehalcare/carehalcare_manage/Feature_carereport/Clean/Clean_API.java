package carehalcare.carehalcare_manage.Feature_carereport.Clean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Clean_API {
    @Headers("Content-Type: application/json")

    @GET("scleanliness/{id}")
    Call<Clean_ResponseDTO> getDataClean_detail(@Query("id") Long id);


    @GET("scleanliness/list/{userId}/{puserId}")
    Call<List<Clean_ResponseDTO>> getDataClean(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );
}
