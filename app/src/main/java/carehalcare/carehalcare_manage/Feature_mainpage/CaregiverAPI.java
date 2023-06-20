package carehalcare.carehalcare_manage.Feature_mainpage;

import carehalcare.carehalcare_manage.Feature_mainpage.Feature_findcid.CaregiverDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CaregiverAPI {
    @GET("caregivers")
    Call<UserDTO> getCaregiverInfo(@Query("userId") String userId);

    @POST("caregivers")
    Call<Long> PostCaregiver(@Body CaregiverDTO caregiverDTO);
}
