package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import com.google.gson.annotations.SerializedName;

public class Meal_Image {
    @SerializedName("id")
    Long id;
    @SerializedName("originalFilename")
    String originalFilename;
    @SerializedName("storeFilename")
    String storeFilename;
    @SerializedName("filePath")
    String filePath;
    @SerializedName("mealId")
    Long mealId;
    @SerializedName("encodedString")
    String encodedString;

    public Meal_Image(String encodedString, String filePath, Long id, Long mealId, String originalFilename, String storeFilename ) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.filePath = filePath;
        this.mealId = mealId;
        this.encodedString = encodedString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStoreFilename() {
        return storeFilename;
    }

    public void setStoreFilename(String storeFilename) {
        this.storeFilename = storeFilename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public String getEncodedString() {
        return encodedString;
    }

    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }
}
