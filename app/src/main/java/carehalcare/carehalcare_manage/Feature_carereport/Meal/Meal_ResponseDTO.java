package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meal_ResponseDTO {
    @SerializedName("id")
    Long id;
    @SerializedName("userId")
    String userId;
    @SerializedName("puserId")
    String puserId;
    @SerializedName("content")
    String content;
    @SerializedName("images")
    List<Meal_Image> images;
    @SerializedName("createdDateTime")
    String createdDateTime;

    Bitmap image;
    public Meal_ResponseDTO( Long id, String userId, String puserId, String content, List<Meal_Image> images, String createdDateTime)
    {
        this.content = content;
        this.createdDateTime = createdDateTime;
        this.images = images;
        this.id = id;
        this.puserId = puserId;
        this.userId = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Meal_Image> getImages() {
        return images;
    }

    public void setImages(List<Meal_Image> images) {
        this.images = images;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDateTime = createdDateTime;
    }
}
