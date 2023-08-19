package carehalcare.carehalcare_manage.Feature_carereport.Bowel;

import com.google.gson.annotations.SerializedName;

public class Bowel_text {

    @SerializedName("createdDateTime")    String createdDateTime;
    @SerializedName("userId")    String userId;
    @SerializedName("puserId")    String puserId;
    @SerializedName("id")    Long id;
    @SerializedName("count")    Long count;
    @SerializedName("content")    String content;

    public Bowel_text(Long id, String userId,String puserId, Long count, String content, String createdDateTime) {
        this.id = id;
        this.userId = userId;
        this.puserId = puserId;
        this.count = count;
        this.content = content;
        this.createdDateTime = createdDateTime;
    }
    public String getCreatedDateTime() {return createdDateTime;}
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
