package carehalcare.carehalcare_manage.Feature_carereport.Clean;

import com.google.gson.annotations.SerializedName;

public class Clean_ResponseDTO {
    //간병인아이디
    @SerializedName("userId")
    String userId = "";
    //보호자아이디
    @SerializedName("puserId")
    String puserId="";

    @SerializedName("id")
    Long id;

    @SerializedName("cleanliness")
    String cleanliness;

    @SerializedName("content")
    String content;

    public Clean_ResponseDTO(String userId, String puserId, String cleanliness, String content) {
        this.userId = userId;
        this.puserId = puserId;
        this.cleanliness = cleanliness;
        this.content = content;
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

    public String getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(String cleanliness) {
        this.cleanliness = cleanliness;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
