package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Meal_API {
    String URL = "http://192.168.0.77:8080/";

    @GET("meals/list/{userId}/{puserId}")
    Call<List<Meal_ResponseDTO>> getDatameal(
            @Path("userId") String userId,
            @Path("puserId") String puserId);

}
