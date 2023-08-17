package carehalcare.carehalcare_manage.Feature_carereport;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Active.ActiveFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Allmenu.AllmenuFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Bowel.BowelFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Clean.CleanFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.MealFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.MedicineFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Sleep.SleepFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Walk.WalkFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Wash.WashFragment;
import carehalcare.carehalcare_manage.Feature_mainpage.MainActivity;
import carehalcare.carehalcare_manage.R;

import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecordActivity extends AppCompatActivity {
    private FrameLayout container;

    private String userid , puserid;
    Button btn_home;
    public void deleteview(){
        container.removeAllViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writemain);
        container = (FrameLayout) findViewById(R.id.container_menu);

        Intent intent = getIntent();
        userid = TokenUtils.getCUser_Id("CUser_Id");
        puserid = TokenUtils.getUser_Id("User_Id");

        btn_home = (Button)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_home);
                finish();
            }
        });

    }


    //투약정보
    public void onMedicine(View view) {
        deleteview();
        MedicineFragment MFragment = new MedicineFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        MFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, MFragment);
        transaction.commit();

    }

    //Sclean
    public void onClean(View view) {
        deleteview();
        CleanFragment cFragment = new CleanFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        cFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, cFragment);
        transaction.commit();
    }



    //Pclean
    public void onWash(View view) {
        deleteview();
        WashFragment washFragment = new WashFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        washFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, washFragment);
        transaction.commit();
    }

    //Bowel
    public void onToilet(View view) {
        deleteview();
        BowelFragment bFragment = new BowelFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        bFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, bFragment);
        transaction.commit();

    }

    //Active
    public void onActive(View view) {
        deleteview();
        ActiveFragment activeFragment = new ActiveFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        activeFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, activeFragment);
        transaction.commit();

    }

    //Sleep
    public void onSleep(View view) {
        deleteview();
        SleepFragment sleepFragment = new SleepFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        sleepFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, sleepFragment);
        transaction.commit();
    }

    public void onWalk(View view) {
        deleteview();
        WalkFragment walkFragment = new WalkFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        walkFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, walkFragment);
        transaction.commit();

    }

    //Meal
    public void onMeal(View view) {
        deleteview();
        MealFragment mealFragment = new MealFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        mealFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, mealFragment);
        transaction.commit();

    }

    public void onall(View view) {
        deleteview();
        AllmenuFragment allmenuFragment = new AllmenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("userid",userid);
        bundle.putString("puserid",puserid);

        allmenuFragment.setArguments(bundle);
        transaction.replace(R.id.container_menu, allmenuFragment);
        transaction.commit();

    }
}
