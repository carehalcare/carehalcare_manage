package carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.Feature_mainpage.MainActivity;
import carehalcare.carehalcare_manage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PInfoEditActivity extends AppCompatActivity {

    private ImageButton btn_home;
    private Button btn_reg;
    private RadioButton btn_man, btn_woman;
    private Retrofit retrofit;
    private PInfoApi pInfoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientinfo);

        EditText et_id = (EditText) findViewById(R.id.et_id);
        EditText et_date = (EditText) findViewById(R.id.et_date);
        EditText et_disease = (EditText) findViewById(R.id.et_disease);
        EditText et_hospital = (EditText) findViewById(R.id.et_hospital);
        EditText et_medicine = (EditText) findViewById(R.id.et_medicine);
        EditText et_personal = (EditText) findViewById(R.id.et_personal);

        btn_home = (ImageButton) findViewById(R.id.btn_homeinfo);
        btn_reg = (Button) findViewById(R.id.btn_register);
        btn_man = (RadioButton) findViewById(R.id.btn_man);
        btn_woman = (RadioButton) findViewById(R.id.btn_woman);

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL.url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //파싱등록
                .build();

        pInfoApi = retrofit.create(PInfoApi.class);


        String intent_pinfo = getIntent().getStringExtra("intent_pinfo");
        String[] lines = intent_pinfo.split("\n"); // 개행 문자('\n')를 기준으로 텍스트를 분리하여 배열로 저장
        Log.d("인텐트값 확인", "intent_pinfo: " + intent_pinfo);

        String userId = "userid1";

        for (String line : lines) {
            if (line.contains(": ")) {
                String[] parts = line.split(": "); // ':' 뒤의 값을 추출하기 위해 문자열을 분리하여 배열로 저장
                if (parts.length >= 2) {
                    String key = parts[0].trim(); // 변수 이름을 추출하고 공백을 제거하여 할당
                    String value = parts[1].trim(); // ':' 뒤의 값을 추출하고 공백을 제거하여 할당

                    // 변수 값에 따라 처리
                    if (key.equals("이름")) {
                        String beforepname = value;
                        et_id.setText(beforepname);
                    } else if (key.equals("생년월일")) {
                        String beforepbirthDate = value.replaceAll("\\D", "");
                        et_date.setText(beforepbirthDate);
                    } else if (key.equals("질환")) {
                        String beforedisease = value;
                        et_disease.setText(beforedisease);
                    } else if (key.equals("담당병원")) {
                        String beforehospital = value;
                        et_hospital.setText(beforehospital);
                    } else if (key.equals("투약정보")) {
                        String beforemedicine = value;
                        et_medicine.setText(beforemedicine);
                    } else if (key.equals("성격")) {
                        String beforepersonal = value;
                        et_personal.setText(beforepersonal);
                    } else if (key.equals("성별")) {
                        if (value.equals("남성")) {
                            btn_man.setChecked(true);
                        } else if (value.equals("여성")) {
                            btn_woman.setChecked(true);
                        } else {
                            // 라디오 버튼이 선택되지 않은 경우에 대한 처리
                            btn_woman.setChecked(false);
                            btn_man.setChecked(false);
                        }
                    }
                    Log.d("Value", "Key: " + key + ", Value: " + value);
                }
            }
        }

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PInfoEditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pname = et_id.getText().toString();
                String pbirthDate = et_date.getText().toString();
                String disease = et_disease.getText().toString();
                String hospital = et_hospital.getText().toString();
                String medicine = et_medicine.getText().toString();
                String personal = et_personal.getText().toString();
                String psex;

                if (btn_man.isChecked()) {
                    psex = "M";
                } else if (btn_woman.isChecked()) {
                    psex = "F";
                } else {
                    psex = "";
                }

                //유효성 검사

                if (!pname.isEmpty() && !pbirthDate.isEmpty() && !disease.isEmpty() && !hospital.isEmpty() &&
                        !medicine.isEmpty() && !personal.isEmpty() && !psex.isEmpty()) {

                    if (pbirthDate.length() != 8) {
                        Toast.makeText(PInfoEditActivity.this, "생년월일은 8자리로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // PatientInfo 객체 생성 및 필드 설정
                    PatientInfo patientInfo = new PatientInfo();

                    patientInfo.setPname(pname);
                    patientInfo.setPbirthDate(pbirthDate);
                    patientInfo.setPsex(psex);
                    patientInfo.setDisease(disease);
                    patientInfo.setHospital(hospital);
                    patientInfo.setMedicine(medicine);
                    patientInfo.setRemark(personal);
                    patientInfo.setUserId(userId);

                    Call<Long> call = pInfoApi.putDataInfo(patientInfo);
                    call.enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            if (response.isSuccessful()) {
                                Log.d("연결 성공", "Status Code : " + response.code());
                                finish();
                            } else {
                                Log.e("연결 실패", "Status Code : " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {
                            Log.e("통신 실패", t.getMessage());
                        }
                    });
                }
                else{
                    // Check if any field is empty
                    StringBuilder missingFields = new StringBuilder();
                    if (pname.isEmpty()) {missingFields.append("이름 ");}
                    if (pbirthDate.isEmpty()) {missingFields.append("생년월일 ");}
                    if (disease.isEmpty()) {missingFields.append("질병 ");}
                    if (hospital.isEmpty()) {missingFields.append("병원 ");}
                    if (medicine.isEmpty()) {missingFields.append("약 ");}
                    if (personal.isEmpty()) {missingFields.append("성격 ");}
                    if (psex.isEmpty()) {missingFields.append("성별 ");}

                    Toast.makeText(PInfoEditActivity.this, missingFields.toString() +
                            "항목이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}