package carehalcare.carehalcare_manage.Feature_carereport.Clean;

import com.google.gson.annotations.SerializedName;

public class Clean_text_modified extends Clean_ResponseDTO {

    private boolean isModified;

    public Clean_text_modified(Long id, String userId, String puserId, String cleanliness, String content, String createdDateTime, boolean isModified) {
        super( id, userId, puserId, cleanliness, content, createdDateTime);
        this.isModified = isModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }
}
