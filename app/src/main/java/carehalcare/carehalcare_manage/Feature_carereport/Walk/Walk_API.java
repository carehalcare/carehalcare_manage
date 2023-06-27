package carehalcare.carehalcare_manage.Feature_carereport.Walk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Walk_API {

    @GET("walks/list/{userId}/{puserId}")
    Call<List<Walk_ResponseDTO>> getDataWalk(
            @Path("userId") String userId,
            @Path("puserId") String puserId);

    @GET("meals/{id}")
    Call<Walk_ResponseDTO> getdatawalk2(
            @Path("id") Long id
    );
}
