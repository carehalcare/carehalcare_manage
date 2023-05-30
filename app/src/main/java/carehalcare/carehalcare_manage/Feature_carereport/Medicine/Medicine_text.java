package carehalcare.carehalcare_manage.Feature_carereport.Medicine;
import com.google.gson.annotations.SerializedName;

public class Medicine_text {

    @SerializedName("createdDateTime")
    String createdDateTime;
    @SerializedName("time")
    String time;  //시간(아침,점심,저녁)
    @SerializedName("mealStatus")
    String mealStatus = ""; //상태(공복,식전,식후)
    @SerializedName("medicine")
    String medicine; //약 종류

    //간병인아이디
    @SerializedName("userId")
    String userId;
    //보호자아이디
    @SerializedName("puserId")
    String puserId;
    @SerializedName("id")
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicine_text(String createdDateTime, String time, String mealStatus, String medicine, String userId, String puserId, Long id) {
        this.createdDateTime = createdDateTime;
        this.time = time;
        this.mealStatus = mealStatus;
        this.medicine = medicine;
        this.userId = userId;
        this.puserId = puserId;
        this.id = id;
    }

    public String getCreatedDateTime() { return createdDateTime;  }
    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userId) {
        this.userId = userId;
    }

    public String getPuserid() {
        return puserId;
    }

    public void setPuserid(String puserId) {
        this.puserId = puserId;
    }

    public String gettime() {
        return time;
    }

    public void settime(String medicine_time) {
        this.time = time;
    }

    public String getmealStatus() {
        return mealStatus;
    }

    public void setmealStatus(String medicine_state) {
        this.mealStatus = medicine_state;
    }

    public String getmedicine() {
        return medicine;
    }

    public void setmedicine(String medicine_name) {
        this.medicine = medicine_name;
    }


}
