package carehalcare.carehalcare_manage.Feature_carereport.Active;


public class Active_text_modified extends Active_text {

    private boolean isModified;

    public Active_text_modified(Long id, String userId, String puserId, String rehabilitation, String walkingAssistance, String position, String createdDateTime, boolean isModified) {
        super( id, userId, puserId, rehabilitation, walkingAssistance, position, createdDateTime);
        this.isModified = isModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }
}