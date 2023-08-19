package carehalcare.carehalcare_manage.Feature_carereport.Medicine;


public class Medicine_text_modified extends Medicine_text {

    private boolean isModified;

    public Medicine_text_modified(Long id, String userId, String puserId, String time, String mealStatus, String medicine, String createdDateTime, boolean isModified) {
        super(id, userId, puserId, time, mealStatus, medicine, createdDateTime);
        this.isModified = isModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

}
