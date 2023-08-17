package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_texthist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Meal_API {

    @GET("meals/list/{userId}/{puserId}")
    Call<List<Meal_ResponseDTO>> getDatameal(
            @Path("userId") String userId,
            @Path("puserId") String puserId);

    @GET("meals/{id}")
    Call<Meal_ResponseDTO> getDatameal_detail(
            @Path("id") Long id );

    @GET("mealhists/list/{id}")
    Call<List<Meal_texthist>> gethistMeal(
            @Path("id") Long id
    );

    //상세 조회
    @GET("mealhists/{id}/{revNum}")
    Call<List<Meal_texthist>> gethistMeal_detail(
            @Path("id") Long id,
            @Path("revNum") int revNum);

}
