package carehalcare.carehalcare_manage.Feature_carereport.Sleep;

import com.google.gson.annotations.SerializedName;

public class Sleep_text {
    @SerializedName("state") String state;
    @SerializedName("content") String content;
    //간병인아이디
    @SerializedName("userId") String userId;
    //보호자아이디
    @SerializedName("puserId") String puserId;

    @SerializedName("id") Long id;
    @SerializedName("createdDateTime") String createdDateTime;


    public Sleep_text(String content, String createdDateTime, Long id, String puserId, String state, String userId) {
        this.userId = userId;
        this.puserId = puserId;
        this.state = state;
        this.content = content;
        this.createdDateTime = createdDateTime;
        this.id = id;
    }

    public String getCreatedDateTime() { return createdDateTime; }
    public String getState() { return state;}

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPuserId() {return puserId;}
    public void setPuserId(String puserId) {this.puserId = puserId;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
}
