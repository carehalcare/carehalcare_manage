package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.DividerItemDecorator;
import carehalcare.carehalcare_manage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealFragment extends Fragment {
    String userid,puserid;
    Long ids;

    private ArrayList<Meal_ResponseDTO> mealArrayList;
    private Meal_adapter mealAdapter;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Meal_API.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public MealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout,container,false);

        userid = this.getArguments().getString("userid1");
        puserid = this.getArguments().getString("puserid1");


        RecyclerView mrecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getContext());
        mrecyclerView.setLayoutManager(mlayoutManager);

        mealArrayList = new ArrayList<>();
        mealAdapter = new Meal_adapter(mealArrayList);
        mrecyclerView.setAdapter(mealAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mrecyclerView.addItemDecoration(dividerItemDecoration);

        getmeallsit();

        mealAdapter.setOnItemClickListener(new Meal_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Meal_ResponseDTO detail_meal_text = mealArrayList.get(position);
                Meal_API meal_service = retrofit.create(Meal_API.class);

                meal_service.getDatameal(userid, puserid).enqueue(new Callback<List<Meal_ResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<Meal_ResponseDTO>> call, Response<List<Meal_ResponseDTO>> response) {
                        if (response.body() != null) {
                            List<Meal_ResponseDTO> datas = response.body();
                            if (datas != null) {
                                ids = datas.get(position).getId();

                                Log.e("지금 position : ", position + "이고 DB ID는 : " + ids);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Meal_ResponseDTO>> call, Throwable t) {
                        Log.e("통신에러", "+" + t.toString());
                        Toast.makeText(getContext(), "통신에러", Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.meal_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                List<Meal_Image> images = detail_meal_text.getImages();

                final ImageView iv_meal_detail = dialog.findViewById(R.id.iv_meal_detail);
                final TextView tv_meal_detail = dialog.findViewById(R.id.tv_meal_detail);

                String imagePath = images.get(0).getFilePath();

                Glide.with(dialog.getContext()).load(new File(imagePath)).into(iv_meal_detail);
                tv_meal_detail.setText(detail_meal_text.getContent());


                final Button btn_meal_detail = dialog.findViewById(R.id.btn_meal_detail);
                btn_meal_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    public void getmeallsit(){
        Meal_API meal_service = retrofit.create(Meal_API.class);
        meal_service.getDatameal(userid, puserid).enqueue(new Callback<List<Meal_ResponseDTO>>() {
            @Override
            public void onResponse(Call<List<Meal_ResponseDTO>> call, Response<List<Meal_ResponseDTO>> response) {
                if (response.body() != null) {
                    List<Meal_ResponseDTO> datas = response.body();
                    String times;
                    String filePath;
                    for (int i = 0; i < datas.size(); i++) {
                        List<Meal_Image> images =datas.get(i).getImages();
                        times = datas.get(i).getCreatedDateTime();
                        filePath = "";

                        if (images != null && !images.isEmpty())
                        {
                            filePath = images.get(0).getFilePath();
                        }
                        Meal_ResponseDTO dict_0 = new Meal_ResponseDTO(datas.get(i).getContent(),
                                times, datas.get(i).getId(), images, puserid, userid);

                        mealArrayList.add(dict_0);
                        mealAdapter.notifyItemInserted(0);
                        Log.e("음식리스트 날짜 출력", ""+response.body().get(i).getCreatedDateTime());
                    }
                    Log.e("getDatameal end", "======================================");

                }}
            @Override
            public void onFailure(Call<List<Meal_ResponseDTO>> call, Throwable t) {
                Log.e("통신에러","+"+t.toString());
                Toast.makeText(getContext(), "통신에러", Toast.LENGTH_SHORT).show();
            }
        });

    }
}