package carehalcare.carehalcare_manage.Feature_carereport.Bowel;

import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_texthist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Bowel_API {

    @Headers("Content-Type: application/json")

    @GET("bowelmovements/{id}")
    Call<Bowel_text> getDataBowel_detail(@Path("id") Long id);

    @GET("bowelmovements/list/{uid}/{puid}")
    Call<List<Bowel_text>> getDataBowel(
            @Path("uid") String userId,
            @Path("puid") String puserId
    );

    @GET("bowelmovementhists/list/{id}")
    Call<List<Bowel_texthist>> gethistBowel(
            @Path("id") Long id
    );

    //상세 조회
    @GET("bowelmovementhists/{id}/{revNum}")
    Call<List<Bowel_texthist>> gethistBowel_detail(
            @Path("id") Long id,
            @Path("revNum") int revNum);

}
