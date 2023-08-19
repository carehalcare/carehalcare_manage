package carehalcare.carehalcare_manage.Feature_carereport.Sleep;


public class Sleep_text_modified extends Sleep_text {

    private boolean isModified;

    public Sleep_text_modified(Long id, String userId, String puserId, String state, String content, String createdDateTime, boolean isModified) {
        super(id, userId, puserId, state, content, createdDateTime);
        this.isModified = isModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

}
