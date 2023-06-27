package carehalcare.carehalcare_manage.Feature_carereport.Allmenu;

import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_Image;

public class AllmenuDTO {
    private Long id;
    private String userId;
    private String puserId;

    private String rehabilitation;
    private String walkingAssistance;
    private String position;

    private String time;
    private String mealStatus;
    private String medicine;

    private Long count;
    private String content;

    private List<Meal_Image> images;

    private String cleanliness;
    private String part;

    private String state;

    private String category;
    private String createdDateTime;

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

    public String getRehabilitation() {
        return rehabilitation;
    }

    public void setRehabilitation(String rehabilitation) {
        this.rehabilitation = rehabilitation;
    }

    public String getWalkingAssistance() {
        return walkingAssistance;
    }

    public void setWalkingAssistance(String walkingAssistance) {
        this.walkingAssistance = walkingAssistance;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMealStatus() {
        return mealStatus;
    }

    public void setMealStatus(String mealStatus) {
        this.mealStatus = mealStatus;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
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

    public String getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(String cleanliness) {
        this.cleanliness = cleanliness;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
