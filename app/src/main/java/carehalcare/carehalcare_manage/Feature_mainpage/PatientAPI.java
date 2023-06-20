package carehalcare.carehalcare_manage.Feature_mainpage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PatientAPI {
    @GET("patients")
    Call<UserDTO> getPatientInfo(@Query("userId") String userId);
}
