package carehalcare.carehalcare_manage.Feature_mainpage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NoticeApi {

    @POST("/notices")
    Call<List<Notice>> createNotice(@Body Notice notice);

    @GET("/notices/list/{puid}")
    Call<List<Notice>> getNotice(@Path("puid") String puid);

    @DELETE("/notices/{id}")
    Call<List<Notice>> DelNotice(@Path("id") String id);

    @PUT("/notices")
    Call<List<Notice>> Change(@Body ChangeNotice changenotice);

}