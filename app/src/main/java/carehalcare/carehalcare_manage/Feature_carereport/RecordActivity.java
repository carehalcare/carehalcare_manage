package carehalcare.carehalcare_manage.Feature_carereport;

import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Bowel.Bowel_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_API;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Sleep.Sleep_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Walk.Walk_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Wash.Wash_adapter;
import carehalcare.carehalcare_manage.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecordActivity extends AppCompatActivity {
    private FrameLayout container;
    private static final int REQUEST_CODE = 1099;
    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int MY_PERMISSION_CAMERA2 = 1112;
    String mCurrentPhotoPath;
    Uri imageUri;

    private Medicine_adapter medicineAdapter;
    private Active_adapter activeAdapter;
    private Clean_adapter cleanAdapter;
    private Bowel_adapter bowelAdapter;
    private Wash_adapter washAdapter;
    private Sleep_adapter sleepAdapter;
    private Meal_adapter mealAdapter;
    private Walk_adapter walkAdapter;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Meal_API.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writemain);
        container = (FrameLayout) findViewById(R.id.container_menu);
    }
}
