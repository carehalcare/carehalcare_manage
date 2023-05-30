package carehalcare.carehalcare_manage.Feature_mainpage.Feature_commute;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommuteActivity extends AppCompatActivity {
    Tag myTag;
    Context context;
    public MaterialCalendarView calendarView;
    public Button check_Btn;
    public TextView diaryTextView,tv_hello,tv_bye;
    Dialog dialog01, dialog_ornot;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commute);
        calendarView=findViewById(R.id.calendarView);
        diaryTextView=findViewById(R.id.tv_date);
//        check_Btn=findViewById(R.id.btn_check);
        tv_hello=findViewById(R.id.tv_hello);
        tv_bye=findViewById(R.id.tv_bye);
        tv_hello.setVisibility(View.INVISIBLE);
        tv_bye.setVisibility(View.INVISIBLE);
        diaryTextView.setText("날짜를 선택하세요");
        diaryTextView.setTextSize(30);
        diaryTextView.setVisibility(View.VISIBLE);

        dialog01 = new Dialog(CommuteActivity.this);       // Dialog 초기화
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
//        dialog01.setContentView(R.layout.dialog_nfc_newcustom);

        dialog_ornot = new Dialog(CommuteActivity.this);       // Dialog 초기화
        dialog_ornot.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
//        dialog_ornot.setContentView(R.layout.dialog_nfc_ornot);

        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tv_hello.setVisibility(View.VISIBLE);
                tv_bye.setVisibility(View.VISIBLE);
//                check_Btn.setVisibility(View.VISIBLE);
//                check_Btn.setBackgroundResource(R.drawable.nfc_dialog_radius);
                int year = date.getYear();
                int month = date.getMonth();
                int dayOfMonth = date.getDay();
                Log.e("출퇴근여부 : ","+"+month);

                diaryTextView.setText(String.format("%d년 %d월 %d일",year,month,dayOfMonth));
                tv_hello.setText("출근기록이 없습니다");
                tv_bye.setText("퇴근기록이 없습니다");
//                check_Btn.setEnabled(true);
//                check_Btn.setText("출근하기");

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");
                SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM");
                SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("dd");

                int years = Integer.parseInt(simpleDateFormatYear.format(mDate));
                int months = Integer.parseInt(simpleDateFormatMonth.format(mDate));
                int days = Integer.parseInt(simpleDateFormatDay.format(mDate));
                if(year<=years && month<=months && dayOfMonth<days){
//                    check_Btn.setEnabled(false);
//                    check_Btn.setText("기록할 수 없습니다");
//                    check_Btn.setBackgroundResource(R.drawable.nfc_enable_design);
                }else if(year>=years && month>=months && dayOfMonth>days){
//                    check_Btn.setEnabled(false);
//                    check_Btn.setText("기록할 수 없습니다");
//                    check_Btn.setBackgroundResource(R.drawable.nfc_enable_design);
                }

                String smonth;
                if(month < 10){int mmont = month; smonth = "0"+mmont;} else{smonth = ""+month;}
                String dtext = year+"-"+smonth+"-"+dayOfMonth;


                CommuteAPI commuteAPI = retrofit.create(CommuteAPI.class);
                commuteAPI.getDataCommute(dtext,"userid1","puserid1").enqueue(new Callback<List<CommuteResponseDto>>() {
                    @Override
                    public void onResponse(Call<List<CommuteResponseDto>> call, Response<List<CommuteResponseDto>> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                for (int i = 0; i < response.body().size(); i++) {
                                    if (response.body().get(i).getCategory().equals("0")){
                                        tv_hello.setText("출근시간 : "+response.body().get(i).getDate()+" "+response.body().get(i).getTime());
//                                        check_Btn.setText("퇴근하기");

                                    } else{
                                        tv_bye.setText("퇴근시간 : "+response.body().get(i).getDate()+" "+response.body().get(i).getTime());
//                                        check_Btn.setText("기록할 수 없습니다");
//                                        check_Btn.setBackgroundResource(R.drawable.nfc_enable_design);
                                    }
                                    Log.e("출퇴근여부 : " + i, response.body().get(i).getCategory()+" "+response.body().get(i).getDate() +
                                            " "+response.body().get(i).getTime());
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<CommuteResponseDto>> call, Throwable t) {
                        Log.e("실패",t.toString());
                    }
                });

                checkDay(year,month,dayOfMonth,"userID");

            }
        });

//        check_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (check_Btn.getText().toString().equals("출근하기")) {
//                    // Viewdialog alert = new Viewdialog();
//                    //alert.showDialog(CommuteActivity.this);
//                    showDialog01();
//                } else if (check_Btn.getText().toString().equals("퇴근하기")) {
//                    showDialog02();
//                }
//            }
//        });

    }
    public void readfromIntent(Intent intent){
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                ||NfcAdapter.ACTION_TECH_DISCOVERED .equals(action)
                ||NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
        {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs!=null){
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++){
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    private void buildTagViews(NdefMessage[] msgs){
        if (msgs == null || msgs.length == 0) return;
        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = "UTF-8";
        int languageCodeLegnth = payload[0] & 0063;
        try{
            text = new String(payload, languageCodeLegnth+1,
                    payload.length-languageCodeLegnth-1,textEncoding);
        } catch (UnsupportedEncodingException e){
            Log.e("UnsupportedEncoding", e.toString());
        }
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getdates = sdf_date.format(date);
        String gettimes = sdf_time.format(date);
        String getTime = sdf.format(date);
        Log.e("nfc리더 읽기 값 : ",text);
        if(check_Btn.getText().equals("출근하기")){
            tv_hello.setText(getTime+" : ");
            CommuteAPI commuteAPI = retrofit.create(CommuteAPI.class);
            CommuteSaveRequestDto commuteSaveRequestDto = new CommuteSaveRequestDto("userid1","puserid1","0",
                    getdates,gettimes);
            commuteAPI.postDataCommute(commuteSaveRequestDto).enqueue(new Callback<List<CommuteResponseDto>>() {
                @Override
                public void onResponse(Call<List<CommuteResponseDto>> call, Response<List<CommuteResponseDto>> response) {
                    if(response.isSuccessful()){
                        if (response.body()!=null){
                            CommuteResponseDto datas = response.body().get(0);
                            Log.e("여부:",datas.getCategory());
                            Log.e("시간:",datas.getDate()+datas.getTime());
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<CommuteResponseDto>> call, Throwable t) {
                    Log.e("실패:",t.toString());
                }
            });
        }
        else if (check_Btn.getText().equals("퇴근하기")) {
            tv_bye.setText(getTime+" : "+text);
            CommuteAPI commuteAPI = retrofit.create(CommuteAPI.class);
            CommuteSaveRequestDto commuteSaveRequestDto = new CommuteSaveRequestDto("userid1","puserid1","1",
                    getdates,gettimes);
            commuteAPI.postDataCommute(commuteSaveRequestDto).enqueue(new Callback<List<CommuteResponseDto>>() {
                @Override
                public void onResponse(Call<List<CommuteResponseDto>> call, Response<List<CommuteResponseDto>> response) {
                    if(response.isSuccessful()){
                        if (response.body()!=null){
                            CommuteResponseDto datas = response.body().get(0);
                            Log.e("여부:",datas.getCategory());
                            Log.e("시간:",datas.getDate()+datas.getTime());
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<CommuteResponseDto>> call, Throwable t) {
                    Log.e("실패:",t.toString());
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        DismissDialog01();
        if(check_Btn.getText().equals("출근하기")){
        ornotshow(intent);}
        else if (check_Btn.getText().equals("퇴근하기")) {
            ornotshow02(intent);
        }

        //setIntent(intent);
        //readfromIntent(intent);
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        WriteModeOff();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        WriteModeOff();
//    }
//    private void WriteModeOn(){
//        writeMode = true;
//        nfcAdapter.enableForegroundDispatch(this, pendingIntent,writingTagFilters,null);
//    }
    public void  checkDay(int cYear,int cMonth,int cDay,String userID){

        try{


            if(tv_hello.getText()=="출근시간"){
                diaryTextView.setVisibility(View.VISIBLE);
                tv_hello.setText("출근시간");
                tv_bye.setText("퇴근시간");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void DismissDialog01(){
        dialog01.dismiss(); // 다이얼로그 띄우기
    }
    public void ornotshow(Intent intent){
        dialog_ornot.show(); // 다이얼로그 띄우기
        dialog_ornot.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경
        // 아니오 버튼

//        Button noBtn = dialog_ornot.findViewById(R.id.ornot_No);
//        noBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                WriteModeOff();
//                dialog_ornot.dismiss(); // 다이얼로그 닫기
//            }
//        });

//        Button yesBtn = dialog_ornot.findViewById(R.id.ornot_yes);
//        yesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                WriteModeOff();
//                setIntent(intent);
//                readfromIntent(intent);
//
//                WriteModeOff();
//                dialog_ornot.dismiss(); // 다이얼로그 닫기
//                check_Btn.setText("퇴근하기");
//            }
//        });
    }
    public void ornotshow02(Intent intent){
        dialog_ornot.show(); // 다이얼로그 띄우기
        dialog_ornot.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경
        // 아니오 버튼

//        Button noBtn = dialog_ornot.findViewById(R.id.ornot_No);
//        noBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                WriteModeOff();
//                dialog_ornot.dismiss(); // 다이얼로그 닫기
//            }
//        });

//        Button yesBtn = dialog_ornot.findViewById(R.id.ornot_yes);
//        yesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                WriteModeOff();
//                setIntent(intent);
//                readfromIntent(intent);
//                WriteModeOff();
//                dialog_ornot.dismiss(); // 다이얼로그 닫기
//                check_Btn.setText("출퇴근완료");
//                check_Btn.setEnabled(false);
//            }
//        });
    }
}