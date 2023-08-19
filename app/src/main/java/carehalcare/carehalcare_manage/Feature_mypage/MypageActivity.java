package carehalcare.carehalcare_manage.Feature_mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import carehalcare.carehalcare_manage.Feature_login.SplashActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.MainActivity;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageActivity extends AppCompatActivity {

    private String userid;
    private Button btn_logout;
    private ImageButton btn_home;
    private TextView tv_setname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        btn_home = (ImageButton) findViewById(R.id.btn_homeinfo);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        tv_setname = (TextView) findViewById(R.id.tv_name);

        SettingAPI mypageAPI = Retrofit_client.createService(SettingAPI.class, TokenUtils.getAccessToken("Access_Token"));
        Log.e("토큰 이름",TokenUtils.getUser_Id("User_Id"));
        Log.e("토큰 토큰",TokenUtils.getAccessToken("Access_Token"));

        userid = TokenUtils.getUser_Id("User_Id");

        mypageAPI.getData(userid).enqueue(new Callback<MypageDTO>() {
            @Override
            public void onResponse(Call<MypageDTO> call, Response<MypageDTO> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        MypageDTO name = response.body();

                        tv_setname.setText(name.getUsername());

                    } else{
                        Log.e("토큰받아오기 실패 ","user_name");
                    }
                }else{
                    Log.e("실패" ,"연결이 안되었습니다");
                }
            }
            @Override
            public void onFailure(Call<MypageDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통신실패.", Toast.LENGTH_SHORT).show();
                Log.e("통신실패",t.toString());
                return;
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}