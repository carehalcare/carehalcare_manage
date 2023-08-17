package carehalcare.carehalcare_manage.Feature_carereport.Wash;

import com.google.gson.annotations.SerializedName;

public class Wash_texthist {

    @SerializedName("createdDateTime")    String createdDateTime;
    @SerializedName("modifiedDateTime")    String modifiedDateTime;
    @SerializedName("userId")    String userId;
    @SerializedName("puserId")    String puserId;
    @SerializedName("id")    Long id;
    @SerializedName("cleanliness")    String cleanliness;
    @SerializedName("part")    String part;
    @SerializedName("content")    String content;
    @SerializedName("category")    String category;

    @SerializedName("revNum")    int revNum;

    public Wash_texthist(Long id, String userId, String puserId, String cleanliness, String part, String content, String category, String createdDateTime, String modifiedDateTime) {
        this.id = id;
        this.userId = userId;
        this.puserId = puserId;
        this.cleanliness = cleanliness;
        this.part = part;
        this.content = content;
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

    public String getCleanliness() {return cleanliness;}

    public String getPart() {return part;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
