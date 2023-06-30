package carehalcare.carehalcare_manage.Feature_mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import carehalcare.carehalcare_manage.Feature_carereport.RecordActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_commute.CommuteActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_notice.NoticeActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo.PatientinfoActivity;
import carehalcare.carehalcare_manage.Feature_mypage.MypageActivity;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private Button commute, write, info, noti;
    ImageButton setting;
    TextView tv_welcommsg;
    String user_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commute = (Button) findViewById(R.id.menu1);
        write = (Button) findViewById(R.id.menu2);
        info = (Button) findViewById(R.id.menu3);
        noti = (Button) findViewById(R.id.menu4);
        setting = (ImageButton) findViewById(R.id.btn_setting);
        tv_welcommsg = (TextView)findViewById(R.id.tv_welcomemsg);
        PatientAPI patientAPI = Retrofit_client.createService(PatientAPI.class, TokenUtils.getAccessToken("Access_Token"));
        //CaregiverAPI caregiverAPI = retrofit.create(CaregiverAPI.class);
        Log.e("토큰 이름",TokenUtils.getUser_Id("User_Id"));
        Log.e("토큰 토큰",TokenUtils.getAccessToken("Access_Token"));

        patientAPI.getPatientInfo(TokenUtils.getUser_Id("User_Id")).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        user_name = response.body().getUsername();
                        Log.e("토큰받은 이름 ",user_name);
                        tv_welcommsg.setText(user_name+" 보호자님\n환영합니다.");
                        if (response.body()!=null) {
                            TokenUtils.setCUser_Id(response.body().getCuserId());
                        }
                    } else{
                        Log.e("토큰받아오기 실패 ","user_name");
                    }
                }else{
                    Log.e("실패" ,"연결이 안되었습니다");
                }
            }
            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통신실패.", Toast.LENGTH_SHORT).show();
                Log.e("통신실패",t.toString());
                return;
            }
        });


        commute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommuteActivity.class);
                startActivity(intent);
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PatientinfoActivity.class);
                startActivity(intent);
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });

    }
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finish();
        }    }
}
