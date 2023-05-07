package carehalcare.carehalcare_manage.Feature_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import carehalcare.carehalcare_manage.MainActivity;
import carehalcare.carehalcare_manage.R;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn, newuserbtn;
    ImageButton kakaobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = (Button) findViewById(R.id.btn_login);
        newuserbtn = (Button) findViewById(R.id.btn_newuser);
        kakaobtn = (ImageButton) findViewById(R.id.btn_kakao);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        newuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        kakaobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, KakaologinActivity.class);
                startActivity(intent);
            }
        });
    }
}