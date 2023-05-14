package carehalcare.carehalcare_manage.Feature_mainpage;

import android.app.Dialog;
import android.content.Intent;
import android.media.audiofx.NoiseSuppressor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import carehalcare.carehalcare_manage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class NoticeActivity extends AppCompatActivity {

    private ImageButton btn_home;
    private Button btn_ok;
    private RecyclerView notiview;
    private ArrayList<Notice> notiviewlist;
    private NoticeAdapter noticeadapter;
    private static final String BASEURL = "http://192.168.35.197:8080/";
    private Retrofit retrofit;
    private NoticeApi noticeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //파싱등록
                .build();

        noticeApi = retrofit.create(NoticeApi.class);

        btn_home = (ImageButton) findViewById(R.id.btn_homenoti);
        btn_ok = (Button) findViewById(R.id.notice_insert);
        notiview = (RecyclerView) findViewById(R.id.notice_list);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notiview.setLayoutManager(layoutManager);

        //어댑터 set
        notiviewlist = new ArrayList<>();
        noticeadapter = new NoticeAdapter(notiviewlist);
        notiview.setAdapter(noticeadapter);

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
            }
        });

        getNoticeList();
        noticeadapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 리스트 항목 클릭 시 동작할 코드 작성
                Notice notice = notiviewlist.get(position);

                Dialog dialog = new Dialog(NoticeActivity.this);
                dialog.setContentView(R.layout.notice_detail);

                TextView notidetail = dialog.findViewById(R.id.tv_notidetail);
                notidetail.setText(notice.getContent());

                Button btn_out = dialog.findViewById(R.id.btn_out);
                Button btn_del = dialog.findViewById(R.id.btn_del);

                btn_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<List<Notice>> call = noticeApi.DelNotice(notice.getId());
                        call.enqueue(new Callback<List<Notice>>() {
                            @Override
                            public void onResponse( Call<List<Notice>> call, Response<List<Notice>> response) {
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
                            public void onFailure( Call<List<Notice>> call, Throwable t) {
                                Log.e("통신 실패", t.getMessage());
                                dialog.dismiss(); // 다이얼로그 닫기
                            }
                        });
                    }
                });

                dialog.show();
            }
        });
    }

    // 공지사항 등록
    private void Insertnoti() {
        Dialog dialog = new Dialog(NoticeActivity.this);

        dialog.setContentView(R.layout.notice_dialog);
        Button btn_reg = dialog.findViewById(R.id.btn_register);
        EditText et_notice = dialog.findViewById(R.id.et_notice);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // NoticeApi를 사용하여 POST 요청
                String content = et_notice.getText().toString();
                Notice notice = new Notice(content, "userid1");

                Call<List<Notice>> call = noticeApi.createNotice(notice);
                call.enqueue(new Callback<List<Notice>>() {
                    @Override
                    public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                        if (response.isSuccessful()) {
                            // POST 요청이 성공적으로 처리되었을 경우의 동작을 정의합니다.
                            List<Notice> notice = response.body();
                            Log.d("연결 성공", "Status Code : " + response.code());

                        } else {
                            // POST 요청이 실패한 경우의 동작을 정의합니다.
                            Log.e("연결 실패", "Status Code : " + response.code());
                        }
                        dialog.dismiss();
                        getNoticeList();
                    }

                    @Override
                    public void onFailure(Call<List<Notice>> call, Throwable t) {
                        Log.e("통신 실패", t.getMessage());
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }


    //notice 가져오기
    private void getNoticeList() {

        Call<List<Notice>> call = noticeApi.getNotice("userid1");
        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Notice> notices = response.body();

                    notiviewlist.clear();
                    for (Notice notice : notices)
                        notiviewlist.add(notice);
                    noticeadapter.notifyDataSetChanged();

                    Log.d("연결 성공", "Status Code : " + response.code());

                } else {
                    Log.e("연결 실패", "Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Log.e("통신 실패", t.getMessage());
            }

        });
    }
}