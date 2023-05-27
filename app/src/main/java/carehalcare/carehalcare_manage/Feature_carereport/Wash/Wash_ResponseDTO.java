package carehalcare.carehalcare_manage.Feature_carereport.Wash;

import com.google.gson.annotations.SerializedName;

public class Wash_ResponseDTO {
    @SerializedName("userId")
    private String userId;

    @SerializedName("puserId")
    private String puserId;

    @SerializedName("cleanliness")
    private String cleanliness;

    @SerializedName("part")
    private String part;

    @SerializedName("content")
    private String content;

    @SerializedName("id")
    Long id;

    @SerializedName("createdDate")
    String createdDate;

    public Wash_ResponseDTO(String userId, String puserId, String cleanliness, String part, String content) {
        this.userId = userId;
        this.puserId = puserId;
        this.cleanliness = cleanliness;
        this.part = part;
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

    public String getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(String cleanliness) {
        this.cleanliness = cleanliness;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
