package carehalcare.carehalcare_manage.Feature_mypage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface SettingAPI {
    @Headers("Content-Type: application/json")

    @GET("mypage/users")
    Call <MypageDTO> getData(@Query("userId") String userId);

}