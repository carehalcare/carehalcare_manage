package carehalcare.carehalcare_manage.Feature_mainpage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PInfoApi {

    @GET("patients/info/{puid}")
    Call<PatientInfo> getDataInfo(@Path("puid") String puid);

    @PUT("patients/info")
    Call<PatientInfo> putDataInfo(@Body PatientInfo patientInfo);
}