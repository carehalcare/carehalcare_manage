package carehalcare.carehalcare_manage.Feature_mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import carehalcare.carehalcare_manage.Feature_carereport.RecordActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_commute.CommuteActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_notice.NoticeActivity;
import carehalcare.carehalcare_manage.Feature_mainpage.Feature_pinfo.PatientinfoActivity;
import carehalcare.carehalcare_manage.R;

public class MainActivity extends AppCompatActivity {


    private Button commute, write, info, noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commute = (Button) findViewById(R.id.menu1);
        write = (Button) findViewById(R.id.menu2);
        info = (Button) findViewById(R.id.menu3);
        noti = (Button) findViewById(R.id.menu4);


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

    }
}
