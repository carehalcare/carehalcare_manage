package carehalcare.carehalcare_manage.Feature_carereport.Walk;

import com.google.gson.annotations.SerializedName;

public class Walk_Image {
    @SerializedName("id")
    Long id;
    @SerializedName("originalFilename")
    String originalFilename;
    @SerializedName("storeFilename")
    String storeFilename;
    @SerializedName("filePath")
    String filePath;
    @SerializedName("walkId")
    Long walkId;
    @SerializedName("encodedString")
    String encodedString;

    public Walk_Image(Long id, String originalFilename, String storeFilename, String filePath, Long walkId,String encodedString) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.filePath = filePath;
        this.walkId = walkId;
        this.encodedString = encodedString;
    }

    public Long getWalkId() {
        return walkId;
    }

    public void setWalkId(Long walkId) {
        this.walkId = walkId;
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

    public String getEncodedString() {
        return encodedString;
    }

    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }
}
