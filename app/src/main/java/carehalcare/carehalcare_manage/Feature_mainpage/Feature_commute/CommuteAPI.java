package carehalcare.carehalcare_manage.Feature_mainpage.Feature_commute;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommuteAPI {
    String URL = "http://192.168.0.18:8080/";

    @POST("commutes")
    Call<List<CommuteResponseDto>> postDataCommute(
            @Body CommuteSaveRequestDto commuteSaveRequestDto
    );

    @GET("/commutes/{date}/{uid}/{pid}")
    Call<List<CommuteResponseDto>>getDataCommute(
            @Path("date") String date,
            @Path("uid") String userId,
            @Path("pid") String puserId
    );
}
