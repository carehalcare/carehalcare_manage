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
import android.widget.ImageButton;
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

    private ImageButton btn_all, btn_meal, btn_walk, btn_clean, btn_wash, btn_bowel, btn_active, btn_medicine, btn_sleep;
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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(true);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);


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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(true);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);
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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(true);
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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(true);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);

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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(true);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);
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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(true);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);
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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(true);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);
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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(false);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(true);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);

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

        btn_all = findViewById(R.id.btn_all);
        btn_all.setSelected(true);

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setSelected(false);

        btn_meal = findViewById(R.id.btn_meal);
        btn_meal.setSelected(false);

        btn_active = findViewById(R.id.btn_active);
        btn_active.setSelected(false);

        btn_bowel = findViewById(R.id.btn_toilet);
        btn_bowel.setSelected(false);

        btn_sleep = findViewById(R.id.btn_sleep);
        btn_sleep.setSelected(false);

        btn_medicine = findViewById(R.id.btn_medicine);
        btn_medicine.setSelected(false);

        btn_clean = findViewById(R.id.btn_clean);
        btn_clean.setSelected(false);

        btn_wash = findViewById(R.id.btn_wash);
        btn_wash.setSelected(false);

    }
}
