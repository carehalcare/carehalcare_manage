package carehalcare.carehalcare_manage.Feature_carereport.Meal;


import java.util.List;

public class Meal_text_modified extends Meal_ResponseDTO {

    private boolean isModified;

    public Meal_text_modified(Long id, String userId, String puserId, String content, List<Meal_Image> images, String createdDateTime, boolean isModified) {
        super( id, userId, puserId, content, images, createdDateTime);
        this.isModified = isModified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }
}