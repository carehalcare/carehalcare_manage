package carehalcare.carehalcare_manage.Feature_mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import carehalcare.carehalcare_manage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PInfoEditActivity extends AppCompatActivity {

    private static final String BASEURL = "http://192.168.35.197:8080/";
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
                .baseUrl(BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //파싱등록
                .build();

        pInfoApi = retrofit.create(PInfoApi.class);

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
                String userId = "userid1";

                String psex;
                if (btn_man.isChecked()) {
                    psex = "M";
                } else if (btn_woman.isChecked()) {
                    psex = "F";
                } else {
                    // 라디오 버튼이 선택되지 않은 경우에 대한 처리
                    psex = "";
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

                Call<PatientInfo> call = pInfoApi.putDataInfo(patientInfo);
                call.enqueue(new Callback<PatientInfo>() {
                    @Override
                    public void onResponse(Call<PatientInfo> call, Response<PatientInfo> response) {
                        if (response.isSuccessful()) {

                            Log.d("연결 성공", "Status Code : " + response.code());

                        } else {
                            Log.e("연결 실패", "Status Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientInfo> call, Throwable t) {
                        Log.e("통신 실패", t.getMessage());
                    }
                });

                Intent intent = new Intent(PInfoEditActivity.this, PatientinfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
