package carehalcare.carehalcare_manage.Feature_carereport.Allmenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Allmenu_API {
    @GET("boards/{uid}/{puid}")
    Call<List<BoardResponseDto>> getallmenu(
            @Header("Authorization") String header,
            @Path("uid") String userId,
            @Path("puid") String puserId);

}
