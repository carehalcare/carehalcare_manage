package carehalcare.carehalcare_manage.Feature_login;

import com.google.gson.annotations.SerializedName;

public class UserSaveRequestDto {
    @SerializedName("userId")
    private String userId;

    @SerializedName("password")
    private String password;

    @SerializedName("username")
    private String username;

    @SerializedName("birthDate")
    private String birthDate;

    @SerializedName("phone")
    private String phone;

    @SerializedName("sex")
    private String sex;

    @SerializedName("code")
    private Integer code;

    public UserSaveRequestDto(String userId, String password, String username, String birthDate, String phone, String sex, Integer code) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.birthDate = birthDate;
        this.phone = phone;
        this.sex = sex;
        this.code = code;
    }
}
