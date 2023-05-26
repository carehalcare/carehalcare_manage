package carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo;

import com.google.gson.annotations.SerializedName;


public class PatientInfo {

    @SerializedName("pname") String pname;
    @SerializedName("pbirthDate") String pbirthDate;
    @SerializedName("psex") String psex;
    @SerializedName("disease") String disease;
    @SerializedName("hospital") String hospital;
    @SerializedName("medicine") String medicine;
    @SerializedName("remark") String remark;

    @SerializedName("userId") String userId;
   // @SerializedName("id") int id = 0; //간병인, 보호자 식별

    public String getDisease() { return disease; }

    public String getHospital() { return hospital;}

    public String getMedicine() { return medicine;}

    public String getPbirthDate() {return pbirthDate;}

    public String getPname() {return pname;}

    public String getPsex() {return psex;}
    public String getRemark() {return remark;}

    public String getUserId() {return userId;}

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setPbirthDate(String pbirthDate) {
        this.pbirthDate = pbirthDate;
    }

    public void setPsex(String psex) {
        this.psex = psex;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public PatientInfo() {
        this.pname = pname;
        this.pbirthDate = pbirthDate;
        this.psex = psex;
        this.disease = disease;
        this.hospital = hospital;
        this.medicine = medicine;
        this.remark = remark;
        this.userId = userId;
    }

}
