package carehalcare.carehalcare_manage.Feature_carereport.Walk;

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

import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.DividerItemDecorator;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_API;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_Image;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_adapter;
import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalkFragment extends Fragment {
    String userid,puserid;
    Long ids;

    private ArrayList<Walk_ResponseDTO> walkArrayList;
    private Walk_adapter walkAdapter;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public WalkFragment() {
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

        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");


        RecyclerView mrecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getContext());
        mrecyclerView.setLayoutManager(mlayoutManager);

        walkArrayList = new ArrayList<>();
        walkAdapter = new Walk_adapter(walkArrayList);
        mrecyclerView.setAdapter(walkAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mrecyclerView.addItemDecoration(dividerItemDecoration);

        getmeallsit();

        walkAdapter.setOnItemClickListener(new Walk_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Walk_ResponseDTO detail_walk_text = walkArrayList.get(position);
//                Walk_API walk_service = retrofit.create(Walk_API.class);
                Walk_API walk_service = Retrofit_client.createService(Walk_API.class, TokenUtils.getAccessToken("Access_Token"));


                walk_service.getDataWalk(userid, puserid).enqueue(new Callback<List<Walk_ResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<Walk_ResponseDTO>> call, Response<List<Walk_ResponseDTO>> response) {
                        if (response.body() != null) {
                            List<Walk_ResponseDTO> datas = response.body();
                            if (datas != null) {
                                String filePath;
                                filePath = datas.get(0).getImages().get(0).getFilePath();
                                ids = datas.get(position).getId();


                                Log.e("지금 position : ", position + "이고 DB ID는 : " + ids);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Walk_ResponseDTO>> call, Throwable t) {
                        Log.e("통신에러", "+" + t.toString());
                        Toast.makeText(getContext(), "통신에러", Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.walk_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                List<Walk_Image> images = detail_walk_text.getImages();

                final ImageView iv_walk_detail = dialog.findViewById(R.id.iv_walk_detail);


                Glide.with(getContext()).load(detail_walk_text.getImages().get(0).getFilePath()).into(iv_walk_detail);

                final Button btn_walk_detail = dialog.findViewById(R.id.btn_walk_detail);
                btn_walk_detail.setOnClickListener(new View.OnClickListener() {
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
//        Walk_API walk_service = retrofit.create(Walk_API.class);
        Walk_API walk_service = Retrofit_client.createService(Walk_API.class, TokenUtils.getAccessToken("Access_Token"));

        walk_service.getDataWalk(userid, puserid).enqueue(new Callback<List<Walk_ResponseDTO>>() {
            @Override
            public void onResponse(Call<List<Walk_ResponseDTO>> call, Response<List<Walk_ResponseDTO>> response) {
                if (response.body() != null) {
                    List<Walk_ResponseDTO> datas = response.body();
                    String times;
                    String filePath;
                    for (int i = 0; i < datas.size(); i++) {

                        List<Walk_Image> images =datas.get(i).getImages();
                        times = datas.get(i).getCreatedDateTime();
                        Log.e("pic!",datas.get(i).getImages().get(0).getFilePath()+"   "+times);

                        filePath = "";
                        if (images != null && !images.isEmpty())
                        {
                            filePath = images.get(0).getFilePath();
                        }
                        Walk_ResponseDTO dict_0 = new Walk_ResponseDTO(datas.get(i).getId(),
                                datas.get(i).getUserId(),datas.get(i).getPuserId(), images,
                                datas.get(i).getCreatedDateTime());

                        walkArrayList.add(dict_0);
                        walkAdapter.notifyItemInserted(0);
                    }
                    Log.e("getDatameal end", "======================================");

                }}
            @Override
            public void onFailure(Call<List<Walk_ResponseDTO>> call, Throwable t) {
                Log.e("통신에러","+"+t.toString());
                //Toast.makeText(getContext(), "통신에러", Toast.LENGTH_SHORT).show();
            }
        });

    }
}