package carehalcare.carehalcare_manage.Feature_login;

import com.google.gson.annotations.SerializedName;

public class LoginDto {
    @SerializedName("userId")
    private String userId;

    @SerializedName("password")
    private String password;

    public LoginDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
