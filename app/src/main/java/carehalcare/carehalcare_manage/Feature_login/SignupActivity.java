package carehalcare.carehalcare_manage.Feature_login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity{

    Button btn_signup,btn_idcheck;
    EditText et_id,et_pw,et_name,et_date,et_phone,et_pwcheck;
    RadioButton btn_women,btn_man;
    String id,pw,name,date,phone,sex,check_pw;
    private AlertDialog dialog;
    private boolean validate = false;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL.apiURL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignupAPI signupAPI = retrofit.create(SignupAPI.class);


        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_idcheck = (Button)findViewById(R.id.btn_idcheck);

        et_id = (EditText)findViewById(R.id.et_id);
        et_pw = (EditText)findViewById(R.id.et_pw);
        et_name = (EditText)findViewById(R.id.et_name);
        et_date = (EditText)findViewById(R.id.et_date);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_pwcheck = (EditText)findViewById(R.id.et_pwcheck);

        btn_man = (RadioButton)findViewById(R.id.btn_man);
        btn_women = (RadioButton)findViewById(R.id.btn_women);

        btn_idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserEmail = et_id.getText().toString();
                if (validate) {
                    return; //검증 완료
                }
                if (UserEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                signupAPI.getcheckid(UserEmail).enqueue(new Callback<UserResponseDto>() {
                    @Override
                    public void onResponse(Call<UserResponseDto> call, Response<UserResponseDto> response) {
                        if (response.isSuccessful()) {
                            Log.e("중복확인", "");
                            if (response.body() != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                et_id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                                btn_idcheck.setEnabled(false);
                                btn_idcheck.setBackgroundColor(Color.parseColor("#808080"));
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UserResponseDto> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "통신실패.", Toast.LENGTH_SHORT).show();
                        Log.e("통신실패",t.toString());
                        return;
                    }
                });
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = et_id.getText().toString();
                pw = et_pw.getText().toString();
                name = et_name.getText().toString();
                date = et_date.getText().toString();
                phone = et_phone.getText().toString();
                sex = "";
                check_pw = et_pwcheck.getText().toString();

                int code = 1; //1은 보호자

                if (btn_man.isChecked()){
                    sex = "M";
                }
                else if (btn_women.isChecked()){
                    sex = "W";
                }

                //아이디 중복체크 했는지 확인
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (id.equals("") || pw.equals("") || name.equals("") || date.equals("") ||
                        phone.equals("") || sex.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }


                if(pw.equals(check_pw)){
                    UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(id,pw,name,date,phone,sex,code);

                    signupAPI.postsignup(userSaveRequestDto).enqueue(new Callback<UserResponseDto>() {
                        @Override
                        public void onResponse(Call<UserResponseDto> call, Response<UserResponseDto> response) {
                            if (response.isSuccessful()){
                                if (response.body()!=null){
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.\n" +
                                            "로그인을 해주세요", name), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponseDto> call, Throwable t) {

                        }
                    });
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }



            }
        });
    }
}
