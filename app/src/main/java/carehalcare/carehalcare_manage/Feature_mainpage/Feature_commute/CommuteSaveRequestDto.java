package carehalcare.carehalcare_manage.Feature_mainpage.Feature_commute;

import com.google.gson.annotations.SerializedName;

public class CommuteSaveRequestDto {
    @SerializedName("userId")
    private String userId;

    @SerializedName("puserId")
    private String puserId;

    @SerializedName("category")
    private String category;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    public CommuteSaveRequestDto(String userId, String puserId, String category, String date, String time) {
        this.userId = userId;
        this.puserId = puserId;
        this.category = category;
        this.date = date;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPuserId() {
        return puserId;
    }

    public void setPuserId(String puserId) {
        this.puserId = puserId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
