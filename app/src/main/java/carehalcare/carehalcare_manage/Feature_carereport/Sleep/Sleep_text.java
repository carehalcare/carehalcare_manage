package carehalcare.carehalcare_manage.Feature_carereport.Sleep;

import com.google.gson.annotations.SerializedName;

public class Sleep_text {
    @SerializedName("state")
    String state = "";
    @SerializedName("content")
    String content = "";
    String sleepTodayResult;

    //간병인아이디
    @SerializedName("userId")
    String userId = "";
    //보호자아이디
    @SerializedName("puserId")
    String puserId="";

    @SerializedName("id")
    Long id;


    public Sleep_text(String userId, String puserId, String state, String content, Long id) {
        this.userId = userId;
        this.puserId = puserId;
        this.state = state;
        this.content = content;
        this.sleepTodayResult = sleepTodayResult;
        this.id = id;
    }
    public Sleep_text(String userId, String puserId, String state, String content) {
        this.userId = userId;
        this.puserId = puserId;
        this.state = state;
        this.content = content;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSleepTodayResult() {
        return sleepTodayResult;
    }

    public void setSleepTodayResult(String sleepTodayResult) {
        this.sleepTodayResult = sleepTodayResult;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
