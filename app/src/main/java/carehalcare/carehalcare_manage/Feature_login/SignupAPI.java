package carehalcare.carehalcare_manage.Feature_login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SignupAPI {
    @POST("signup")
    Call<UserResponseDto> postsignup(@Body UserSaveRequestDto userSaveRequestDto);

    @POST("login")
    Call<TokenDto> postlogin(@Body LoginDto loginDto);

    @GET("users")
    Call<UserResponseDto> getcheckid(@Query("userId") String userId);
    //가입된 사용자면 null리턴된다.
}
