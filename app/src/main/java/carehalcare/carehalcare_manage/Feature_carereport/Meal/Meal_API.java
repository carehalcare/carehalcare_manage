package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Meal_API {
    String URL = "http://172.30.1.74:8080/";

    @GET("meals/list/{userId}/{puserId}")
    Call<List<Meal_ResponseDTO>> getDatameal(
            @Path("userId") String userId,
            @Path("puserId") String puserId);

    @GET("meals/{id}")
    Call<Meal_ResponseDTO> getDatameal_detail(
            @Path("id") Long id );

}
