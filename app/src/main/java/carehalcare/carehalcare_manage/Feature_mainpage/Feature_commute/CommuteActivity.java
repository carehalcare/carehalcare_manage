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
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collections;
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
    public MaterialCalendarView calendarView;
    public TextView diaryTextView,tv_hello,tv_bye;
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
        tv_hello=findViewById(R.id.tv_hello);
        tv_bye=findViewById(R.id.tv_bye);
        tv_hello.setVisibility(View.INVISIBLE);
        tv_bye.setVisibility(View.INVISIBLE);
        diaryTextView.setText("날짜를 선택하세요");
        diaryTextView.setTextSize(30);
        diaryTextView.setVisibility(View.VISIBLE);
        CommuteAPI commuteAPI = retrofit.create(CommuteAPI.class);
        for (int iday = 1; iday < 31;iday++){
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);
            SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM");

            int years = CalendarDay.today().getYear();
            int months = CalendarDay.today().getMonth();
            int days = iday;
            String dtext = years+"-"+months+"-"+iday;

            commuteAPI.getDataCommute(dtext,"userid1","puserid1").enqueue(new Callback<List<CommuteResponseDto>>() {
                @Override
                public void onResponse(Call<List<CommuteResponseDto>> call, Response<List<CommuteResponseDto>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null){
                            for (int i = 0; i < response.body().size(); i++) {
                                if(response.body().get(response.body().size()-1).getCategory().equals("1")){
                                    calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singletonList(
                                            CalendarDay.from(years,months,days))));}
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<CommuteResponseDto>> call, Throwable t) {
                    Log.e("실패",t.toString());
                }
            });
        }

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                for (int iday = 1; iday < 32;iday++){
                    long now = System.currentTimeMillis();

                    int years = date.getYear();
                    int months = date.getMonth();
                    int days = iday;
                    String dtext = years+"-"+months+"-"+iday;

                    commuteAPI.getDataCommute(dtext,"userid1","puserid1").enqueue(new Callback<List<CommuteResponseDto>>() {
                        @Override
                        public void onResponse(Call<List<CommuteResponseDto>> call, Response<List<CommuteResponseDto>> response) {
                            if(response.isSuccessful()){
                                if(response.body()!=null){
                                    for (int i = 0; i < response.body().size(); i++) {
                                        if(response.body().get(response.body().size()-1).getCategory().equals("1")){
                                            calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singletonList(
                                                    CalendarDay.from(years,months,days))));
                                        }

                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<List<CommuteResponseDto>> call, Throwable t) {
                            Log.e("실패",t.toString());
                        }
                    });
                }
            }
        });
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tv_hello.setVisibility(View.VISIBLE);
                tv_bye.setVisibility(View.VISIBLE);

                int year = date.getYear();
                int month = date.getMonth();
                int dayOfMonth = date.getDay();
                Log.e("출퇴근여부 : ","+"+month);

                diaryTextView.setText(String.format("%d년 %d월 %d일",year,month,dayOfMonth));
                tv_hello.setText("출근기록이 없습니다");
                tv_bye.setText("퇴근기록이 없습니다");

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");
                SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM");
                SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("dd");

                int years = Integer.parseInt(simpleDateFormatYear.format(mDate));
                int months = Integer.parseInt(simpleDateFormatMonth.format(mDate));
                int days = Integer.parseInt(simpleDateFormatDay.format(mDate));

                String smonth;
                if(month < 10){int mmont = month; smonth = "0"+mmont;} else{smonth = ""+month;}
                String dtext = year+"-"+smonth+"-"+"0"+dayOfMonth;


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


            }
        });}


}