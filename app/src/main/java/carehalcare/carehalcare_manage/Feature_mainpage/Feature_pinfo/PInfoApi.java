package carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PInfoApi {

    @Headers("Content-Type: application/json")

    @GET("patients/info/{puid}")
    Call<PatientInfo> getDataInfo(@Path("puid") String puid);

    @PUT("patients/info")
    Call<Long> putDataInfo(@Body PatientInfo patientInfo);

    @POST("patients/info")
    Call<Long> postDataInfo(@Body PatientInfo patientInfo);
}