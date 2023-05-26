package carehalcare.carehalcare_manage.Feature_mainpage.Feature_notice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NoticeApi {
    @Headers("Content-Type: application/json")

    @POST("/notices")
    Call<Long> createNotice(@Body Notice notice);

    @GET("/notices/list/{puid}")
    Call<List<Notice>> getNotice(@Path("puid") String puid);

    @DELETE("/notices/{id}")
    Call<List<Notice>> DelNotice(@Path("id") String id);

    @PUT("/notices")
    Call<Long> Change(@Body ChangeNotice changenotice);

}