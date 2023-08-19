package carehalcare.carehalcare_manage.Feature_carereport.Bowel;


public class Bowel_text_modified extends Bowel_text{

    private boolean isModified;

    public Bowel_text_modified(Long id, String userId,String puserId, Long count, String content, String createdDateTime, boolean isModified) {
        super( id, userId, puserId, count, content, createdDateTime);
        this.isModified = isModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

}
