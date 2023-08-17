package carehalcare.carehalcare_manage.Feature_carereport.Clean;

import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Bowel.Bowel_texthist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Clean_API {
    @Headers("Content-Type: application/json")

    @GET("scleanliness/{id}")
    Call<Clean_ResponseDTO> getDataClean_detail(@Path("id") Long id);


    @GET("scleanliness/list/{userId}/{puserId}")
    Call<List<Clean_ResponseDTO>> getDataClean(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );

    @GET("scleanlinesshists/list/{id}")
    Call<List<Clean_texthist>> gethistSclean(
            @Path("id") Long id
    );

    //상세 조회
    @GET("scleanlinesshists/{id}/{revNum}")
    Call<List<Clean_texthist>> gethistSclean_detail(
            @Path("id") Long id,
            @Path("revNum") int revNum);

}
