package carehalcare.carehalcare_manage.Feature_carereport.Clean;

import com.google.gson.annotations.SerializedName;

public class Clean_text {
    String changeSheet;
    String changeCloth;
    String ventilation;
    String et_cleanForm;

    String cleanTodayResult;

    @SerializedName("createdDateTime")    String createdDateTime;
    @SerializedName("userId")    String userId;
    @SerializedName("puserId")    String puserId;
    @SerializedName("id")    Long id;
    @SerializedName("content")    String content;
    @SerializedName("cleanliness")    String cleanliness;

    public Clean_text(String changeSheet, String changeCloth, String ventilation, String et_cleanForm) {
        this.changeSheet = changeSheet;
        this.changeCloth = changeCloth;
        this.ventilation = ventilation;
        this.et_cleanForm = et_cleanForm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String get_CleanTodayResult() {
        return cleanTodayResult;
    }

    public void set_CleanTodayResult(String cleanTodayResult) { this.cleanTodayResult = cleanTodayResult; }


    public String getChangeSheet() {
        return changeSheet;
    }

    public void setChangeSheet(String changeSheet) {
        this.changeSheet = changeSheet;
    }

    public String getChangeCloth() {
        return changeCloth;
    }

    public void setChangeCloth(String changeCloth) {
        this.changeCloth = changeCloth;
    }

    public String getVentilation() {
        return ventilation;
    }

    public void setVentilation(String ventilation) {
        this.ventilation = ventilation;
    }

    public String getEt_cleanForm() {
        return et_cleanForm;
    }

    public void setEt_cleanForm(String et_cleanForm) {
        this.et_cleanForm = et_cleanForm;
    }
}
