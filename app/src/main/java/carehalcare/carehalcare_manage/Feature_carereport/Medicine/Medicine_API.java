package carehalcare.carehalcare_manage.Feature_carereport.Medicine;

import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_texthist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Medicine_API {

    @Headers("Content-Type: application/json")

    @GET("administrations/{id}")
    Call<Medicine_text>getDatamedicine_detail(@Path("id") Long id);

    @GET("administrations/list/{userId}/{puserId}")
    Call<List<Medicine_text>> getDatamedicine(
            @Path("userId") String userId,
            @Path("puserId") String puserId
    );

    @GET("administrationhists/list/{id}")
    Call<List<Medicine_texthist>> gethistMedi(
            @Path("id") Long id
    );

    //상세 조회
    @GET("administrationhists/{id}/{revNum}")
    Call<List<Medicine_texthist>> gethistMedi_detail(
            @Path("id") Long id,
            @Path("revNum") int revNum
    );
}
