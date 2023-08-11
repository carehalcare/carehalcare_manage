package carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Struct;

import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.Feature_mainpage.MainActivity;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class PatientinfoActivity extends AppCompatActivity {
    private ImageButton btn_home;
    private Button btn_edit, btn_add;
    private TextView tv_info, tv_name;
    Call <PatientInfo> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpatientinfo);

        btn_home = (ImageButton) findViewById(R.id.btn_homeinfo);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_name = (TextView) findViewById(R.id.tv_name);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientinfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PatientinfoActivity.this, PInfoEditActivity.class);

                String intent_pinfo = tv_info.getText().toString();
                String intent_pname = tv_name.getText().toString();

                intent.putExtra("intent_pinfo", intent_pinfo);
                intent.putExtra("intent_pname", intent_pname);
                //Log.d("인텐트값1", intent_pinfo);

                startActivity(intent);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientinfoActivity.this, PInfoAddActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //파싱등록
                .build();

        //PInfoApi infoApi = retrofit.create(PInfoApi.class);
        PInfoApi infoApi = Retrofit_client.createService(PInfoApi.class, TokenUtils.getAccessToken("Token_Access"));

        call = infoApi.getDataInfo(TokenUtils.getUser_Id("User_Id"));
        call.enqueue(new Callback<PatientInfo>() {
            @Override
            public void onResponse(Call<PatientInfo> call, Response<PatientInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        btn_add.setVisibility(View.GONE); // btn_add 비활성화

                        PatientInfo patientInfo = response.body();

                        String pname = patientInfo.getPname();
                        String disease = patientInfo.getDisease();
                        String hospital = patientInfo.getHospital();
                        String medicine = patientInfo.getMedicine();
                        String personal = patientInfo.getRemark();

                        String content = "";
                        //생년월일 출력 방식 변경 0000-00-00 -> 0000년 00월 00일
                        String birthDate = patientInfo.getPbirthDate();
                        if (birthDate.isEmpty() || birthDate.length() != 8) {
                            content += "생년월일 정보 없음 \n\n";
                        } else {
                            String year = birthDate.substring(0, 4);
                            String month = birthDate.substring(4, 6);
                            String day = birthDate.substring(6, 8);
                            String formattedBirthDate = year + "년 " + month + "월 " + day + "일";

                            content += "생년월일: " + formattedBirthDate + "\n\n";
                        }
                        //성별 출력 방식 변경 F, M -> 여성 남성
                        String gender = patientInfo.getPsex();
                        //String gender = "";
                        if (gender.equals("F")) {
                            gender = "여";
                        } else if (gender.equals("M")) {
                            gender = "남";
                        } else {
                            gender = "성별 정보 없음";
                        }

                        String name = "";
                        name += pname +" (" + gender + ")";
                        content += "질환: " + disease + "\n\n";
                        content += "담당병원: " + hospital + "\n\n";
                        content += "투약정보: " + medicine + "\n\n";
                        content += "성격: " + personal + "\n\n";

                        tv_info.setText(content);
                        tv_name.setText(name);

                    } else {
                        tv_info.setText("등록된 정보가 없습니다.\n 정보를 입력해주세요.");
                    }

                    Log.d("연결 성공", "Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PatientInfo> call, Throwable t) {
                Log.e("환자정보불러오기", "실패");
                tv_info.setText("통신 실패");
                t.printStackTrace();
            }
        });
    }
}