package carehalcare.carehalcare_manage.Feature_carereport.Medicine;

import com.google.gson.annotations.SerializedName;

public class Medicine_texthist {

    @SerializedName("createdDateTime")    String createdDateTime;
    @SerializedName("modifiedDateTime")    String modifiedDateTime;
    @SerializedName("userId")    String userId;
    @SerializedName("puserId")    String puserId;
    @SerializedName("id")    Long id;
    @SerializedName("time")    String time;
    @SerializedName("mealStatus")    String mealStatus;
    @SerializedName("medicine")    String medicine;
    @SerializedName("category")    String category;

    @SerializedName("revNum")    int revNum;

    public Medicine_texthist(Long id, String userId, String puserId, String time, String mealStatus, String medicine, String category, String createdDateTime, String modifiedDateTime) {
        this.id = id;
        this.userId = userId;
        this.puserId = puserId;
        this.time =time;
        this.mealStatus = mealStatus;
        this.medicine = medicine;
        this.category = category;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
    public String getCreatedDateTime() {return createdDateTime;}

    public String getModifiedDateTime() { return modifiedDateTime;}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() { return time; }
    public String getMealStatus() {return mealStatus;}

    public String getMedicine() {return medicine;}

}
