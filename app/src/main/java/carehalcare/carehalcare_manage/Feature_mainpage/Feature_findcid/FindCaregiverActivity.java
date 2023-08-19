package carehalcare.carehalcare_manage.Feature_mainpage.Feature_findcid;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import carehalcare.carehalcare_manage.Feature_mainpage.PatientAPI;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import carehalcare.carehalcare_manage.Feature_mainpage.CaregiverAPI;
import carehalcare.carehalcare_manage.Feature_mainpage.MainActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.UserDTO;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindCaregiverActivity extends AppCompatActivity {
    AppCompatButton btn_findId, btn_ok;
    TextView tv_result,tv_findpid_welcomemsg;
    EditText et_getid;
    String cuserId;
    private AlertDialog dialog;

    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findcid);

        btn_findId = (AppCompatButton) findViewById(R.id.btn_idsearch);
        btn_ok = (AppCompatButton) findViewById(R.id.btn_ok);
        tv_result = (TextView) findViewById(R.id.tv_findresult);
        et_getid = (EditText) findViewById(R.id.et_getid);
        tv_findpid_welcomemsg = (TextView)findViewById(R.id.tv_findpid_welcomemsg);
        Log.e("갑자기 토큰 안나온다고..",TokenUtils.getAccessToken("Access_Token"));

        PatientAPI patientAPI = Retrofit_client.createService(PatientAPI.class, TokenUtils.getAccessToken("Access_Token"));

        patientAPI.getPatientInfo(TokenUtils.getUser_Id("User_Id")).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        String user_name = response.body().getUsername();
                        Log.e("토큰받은 이름 ",user_name);
                        tv_findpid_welcomemsg.setText(user_name+" 보호자님\n환영합니다.");}
                    else{Log.e("토큰받아오기 실패 ","user_name");}}
                else{Log.e("실패" ,"연결이 안되었습니다");}}
            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통신실패.", Toast.LENGTH_SHORT).show();
                Log.e("통신실패",t.toString());
                return;}});

        CaregiverAPI caregiverAPI = Retrofit_client.createService(CaregiverAPI.class, TokenUtils.getAccessToken("Access_Token"));

        btn_findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디찾기 버튼을 누르면
                cuserId = et_getid.getText().toString();

                if (validate) {
                    return; //검증 완료
                }
                if (cuserId.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindCaregiverActivity.this);
                    dialog = builder.setMessage("보호자 아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Call<UserDTO> call = caregiverAPI.getCaregiverInfo(cuserId);
                call.enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        if (response.isSuccessful()) {
                            UserDTO pdatas = response.body();
                            if (pdatas != null) {
                                String str = "";
                                String puserId = pdatas.getUserId();
                                if (pdatas.getUserId().equals(puserId)) {
                                    str = "아이디 조회 결과 " + "'" + pdatas.getUsername() + "'" + " 님이 맞습니까?";

                                    AlertDialog.Builder builder = new AlertDialog.Builder(FindCaregiverActivity.this);
                                    dialog = builder
                                            .setMessage(str)
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    et_getid.setEnabled(false);
                                                    validate = true;
                                                    btn_findId.setEnabled(false);
                                                    btn_findId.setBackgroundColor(Color.parseColor("#808080"));
                                                }
                                            })
                                            .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    et_getid.setEnabled(true);
                                                    validate = false;
                                                    btn_findId.setEnabled(true);

                                                }
                                            })
                                            .create();
                                    dialog.show();
                                }
                            }
                        }
                        else {
                            Log.d(TAG, "노데이터 : " + response.code());
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindCaregiverActivity.this);
                            dialog = builder.setMessage("일치하는 아이디가 없습니다.").setNegativeButton("확인", null).create();
                            dialog.show();
                        }
                        if (cuserId == null || cuserId.isEmpty()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindCaregiverActivity.this);
                            dialog = builder.setMessage("아이디가 입력되지 않았습니다.").setNegativeButton("확인", null).create();
                            dialog.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        tv_result.setText(t.getMessage());
                    }
                });

            }
        });

        //등록하기 버튼을 누르면 Post
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디 중복체크 했는지 확인
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindCaregiverActivity.this);
                    dialog = builder.setMessage("간병인 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (cuserId.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindCaregiverActivity.this);
                    dialog = builder.setMessage("간병인 아이디를 입력해주세요").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                // 새로운 PID를 서버에 보내어 등록하기
                CaregiverDTO caregiverDTO = new CaregiverDTO(TokenUtils.getUser_Id("User_Id"), cuserId);
                Call<Long> post = caregiverAPI.PostCaregiver(caregiverDTO);

                post.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> post, Response<Long> response) {
                        if (response.isSuccessful()) {
                            // TODO: 처리할 로직 작성
                            Log.e("보낸다 ========",response.body()+"");
                            TokenUtils.setCUser_Id(cuserId);
                            Toast.makeText(getApplicationContext(), String.format("간병인 등록이 완료되었습니다"),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FindCaregiverActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "오류 발생 : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        tv_result.setText(t.getMessage());
                    }
                });
            }
        });

    }
}
