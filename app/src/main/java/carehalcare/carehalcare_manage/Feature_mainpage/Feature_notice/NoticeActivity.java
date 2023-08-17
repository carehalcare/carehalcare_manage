package carehalcare.carehalcare_manage.Feature_mainpage.Feature_notice;

import static carehalcare.carehalcare_manage.Feature_carereport.DateUtils.formatDatestring;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_findcid.FindCaregiverActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo.PInfoApi;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo.PatientInfo;
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


public class NoticeActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private ImageButton btn_home;
    private Button btn_ok;
    private RecyclerView notiview;
    private ArrayList<Notice> notiviewlist;
    private NoticeAdapter noticeadapter;
    private Retrofit retrofit;
    private NoticeApi noticeApi;
    String userid,cuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        userid = TokenUtils.getUser_Id("User_Id");
        cuserid = TokenUtils.getUser_Id("CUser_Id");
        btn_ok = (Button) findViewById(R.id.notice_insert);

        PInfoApi pInfoApi = Retrofit_client.createService(PInfoApi.class,TokenUtils.getAccessToken("Access_Token"));
        pInfoApi.getDataInfo(userid).enqueue(new Callback<PatientInfo>() {
            @Override
            public void onResponse(Call<PatientInfo> call, Response<PatientInfo> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Log.e("환자정보는", response.body().getPname());
                    }
                    else{
                        Log.e("else 환자정보는", response.body().getPname());
                        Log.e("통신성공노 환자정보는", "null?");
                        AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                        dialog = builder.setMessage("환자정보를 먼저 등록해주세요 입력하세요.").setPositiveButton("확인", null).create();
                        dialog.show();
                        btn_ok.setEnabled(false);
                    }
                } else{
                    Log.e("통신성공노 환자정보는", "null?");
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                    dialog = builder.setMessage("환자정보를 먼저 등록해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    btn_ok.setEnabled(false);

                }
            }

            @Override
            public void onFailure(Call<PatientInfo> call, Throwable t) {
                Log.e("통신오류",t.toString());

            }
        });
        //noticeApi = retrofit.create(NoticeApi.class);
        noticeApi = Retrofit_client.createService(NoticeApi.class, TokenUtils.getAccessToken("Token_Access"));

        btn_home = (ImageButton) findViewById(R.id.btn_homenoti);
        notiview = (RecyclerView) findViewById(R.id.notice_list);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notiview.setLayoutManager(layoutManager);

        //어댑터 set
        notiviewlist = new ArrayList<>();
        noticeadapter = new NoticeAdapter(notiviewlist);
        notiview.setAdapter(noticeadapter);
        getNoticeList();

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insertnoti();
                getNoticeList();
            }
        });

        noticeadapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 리스트 항목 클릭 시 동작할 코드 작성
                Notice notice = notiviewlist.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                View view = LayoutInflater.from(NoticeActivity.this)
                        .inflate(R.layout.notice_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                TextView notidetail = dialog.findViewById(R.id.tv_notidetail);
                TextView notidate = dialog.findViewById(R.id.tv_detaildate);

                String formdate = formatDatestring(notice.getModifiedDate());
                notidetail.setText(notice.getContent());
                notidate.setText(formdate);

                Button btn_out = dialog.findViewById(R.id.btn_out);
                Button btn_del = dialog.findViewById(R.id.btn_del);
                Button btn_change = dialog.findViewById(R.id.btn_change);

                btn_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("삭제하기")
                                .setMessage("삭제하시겠습니까?")
                                .setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Call<List<Notice>> call = noticeApi.DelNotice(notice.getId());
                                        call.enqueue(new Callback<List<Notice>>() {
                                            @Override
                                            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                                                if (response.isSuccessful()) {
                                                    // 삭제 요청이 성공적으로 처리되었을 경우의 동작을 정의합니다.
                                                    Toast.makeText(NoticeActivity.this, "공지가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                    // 공지 목록 갱신
                                                    getNoticeList();
                                                } else {
                                                    // 삭제 요청이 실패한 경우의 동작을 정의합니다.
                                                    Log.e("삭제 실패", "Status Code : " + response.code());
                                                }
                                                dialog.dismiss(); // 다이얼로그 닫기
                                            }

                                            @Override
                                            public void onFailure(Call<List<Notice>> call, Throwable t) {
                                                Log.e("통신 실패", t.getMessage());

                                            }
                                        });
                                    }
                                })
                                .setNeutralButton("취소", null)
                                .show();
                        dialog.dismiss();
                    }
                });

                btn_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                        View cview = LayoutInflater.from(NoticeActivity.this)
                                .inflate(R.layout.notice_dialogchange, null, false);
                        builder.setView(cview);
                        final AlertDialog changedialog = builder.create();
                        changedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        changedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        changedialog.show();
                        Button bchange = changedialog.findViewById(R.id.btn_change);
                        Button undo = changedialog.findViewById(R.id.btn_undo);
                        EditText et_notice = changedialog.findViewById(R.id.et_notice);

                        Notice changeNotice = notiviewlist.get(position);
                        et_notice.setText(changeNotice.getContent());

                        undo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                changedialog.dismiss();
                                dialog.show();
                            }
                        });

                        bchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String content = et_notice.getText().toString();
                                ChangeNotice changenoti = new ChangeNotice(content, changeNotice.getId());

                                Call<Long> call = noticeApi.Change(changenoti);
                                call.enqueue(new Callback<Long>() {
                                    @Override
                                    public void onResponse(Call<Long> call, Response<Long> response) {
                                        if (response.isSuccessful()) {
                                            // PUT
                                            Toast.makeText(NoticeActivity.this, "공지가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                            Log.d("수정 성공", "Status Code : " + response.code());
                                            //상세 dialog를 닫고 리스트 update
                                            dialog.dismiss();
                                            getNoticeList();

                                        } else {
                                            // PUT 실패
                                            Log.e("수정 실패", "Status Code : " + response.code());

                                            try {
                                                String errorBody = response.errorBody().string();
                                                Log.e("서버 오류 메시지", errorBody);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        changedialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<Long> call, Throwable t) {
                                        Log.e("수정 통신 실패", t.getMessage());
                                        changedialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
    //    공지사항 등록
    private void Insertnoti() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
        View view = LayoutInflater.from(NoticeActivity.this)
                .inflate(R.layout.notice_dialog, null, false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        dialog.setContentView(R.layout.notice_dialog);
        Button btn_reg = dialog.findViewById(R.id.btn_register);
        EditText et_notice = dialog.findViewById(R.id.et_notice);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // NoticeApi를 사용하여 POST 요청
                String content = et_notice.getText().toString();
                Notice notice = new Notice(content, userid);

                Call<Long> call = noticeApi.createNotice(notice);
                call.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        Log.e("보낼때====================", response.body()+"");
                        if (response.isSuccessful()) {
                            // POST 요청이 성공적으로 처리
                            Toast.makeText(NoticeActivity.this, "공지가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.d("post 연결 성공", "Status Code : " + response.code());
                            getNoticeList();
                            dialog.dismiss();

                        } else {
                            // POST 요청이 실패한 경우의 동작을 정의
                            Log.e("post 연결 실패", "Status Code : " + response.code());
                            dialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Log.e("등록 통신 실패", t.getMessage());
                        dialog.dismiss();
                    }
                });
                dialog.dismiss();
            }
        });
    }


    //notice 가져오기
    private void getNoticeList() {
        notiviewlist.clear();
        Call<List<Notice>> call = noticeApi.getNotice(userid);
        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Notice> notices = response.body();
                    notiviewlist.addAll(notices);
                    noticeadapter.notifyDataSetChanged();
                    Log.d("get 연결 성공", "Status Code : " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Log.e("get 통신 실패", t.getMessage());
            }
        });
    }

}