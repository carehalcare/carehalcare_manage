package carehalcare.carehalcare_manage.Feature_mainpage.Feature_findcid;

import carehalcare.carehalcare_manage.Feature_mainpage.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FindCaregiverApi {

    @GET("patients")
    Call<UserDTO> getData(@Query("userId") String userId);

    @POST("patients")
    Call<Long> addPID(@Body CaregiverDTO caregiverDTO);
}