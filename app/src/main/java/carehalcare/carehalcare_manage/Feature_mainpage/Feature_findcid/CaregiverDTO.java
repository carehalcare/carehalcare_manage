package carehalcare.carehalcare_manage.Feature_mainpage.Feature_findcid;

import com.google.gson.annotations.SerializedName;

public class CaregiverDTO {
    @SerializedName("userId") String userId;
    @SerializedName("username") String username;
    @SerializedName("cuserId")String cuserId;
    private String Id;

    public CaregiverDTO(String userId, String cuserId) {
        this.cuserId = cuserId;
        this.userId = userId;
    }

    public CaregiverDTO(){}

    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
