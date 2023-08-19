package carehalcare.carehalcare_manage.Feature_carereport.Wash;


public class Wash_text_modified extends Wash_ResponseDTO {

    private boolean isModified;

    public Wash_text_modified(Long id, String userId, String puserId, String cleanliness, String part, String content, String createdDateTime, boolean isModified) {
        super( id, userId, puserId, cleanliness, part, content, createdDateTime);
        this.isModified = isModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }
}
