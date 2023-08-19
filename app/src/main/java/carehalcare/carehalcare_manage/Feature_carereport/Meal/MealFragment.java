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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_API;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_adapterhist;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_texthist;
import carehalcare.carehalcare_manage.Feature_carereport.DividerItemDecorator;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_text_modified;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_texthist;
import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealFragment extends Fragment {
    String userid,puserid;

    Long ids;
    private ArrayList<Meal_text_modified> mealArrayList;
    private Meal_adapter mealAdapter;

    private ArrayList<Meal_texthist> histArrayList;
    private Meal_adapterhist histAdapter;
    private Button btn_off, btn_update;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL.URL)
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

        Meal_API mealApi = Retrofit_client.createService(Meal_API.class, TokenUtils.getAccessToken("Access_Token"));

        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

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

                Meal_ResponseDTO meal_text = mealArrayList.get(position);
                Long clicked = meal_text.getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.meal_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final ImageView iv_meal_detail = dialog.findViewById(R.id.iv_meal_detail);
                final TextView tv_meal_detail = dialog.findViewById(R.id.tv_meal_detail);

                Glide.with(getContext()).load(meal_text.getImages().get(0).getFilePath()).into(iv_meal_detail);

                tv_meal_detail.setText(meal_text.getContent());

                btn_off = dialog.findViewById(R.id.btn_meal_detail);
                btn_update = dialog.findViewById(R.id.btn_update_report);
                btn_off.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.report_change, null, false);
                        builder.setView(dialogView);

                        final AlertDialog dialog = builder.create();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.show();

                        RecyclerView histRecyclerView = dialogView.findViewById(R.id.change_list);
                        LinearLayoutManager histLayoutManager = new LinearLayoutManager(getContext());
                        histRecyclerView.setLayoutManager(histLayoutManager);

                        // Create and set Meal_adapterhist adapter for the histRecyclerView
                        histArrayList = new ArrayList<>();
                        histAdapter = new Meal_adapterhist(histArrayList);
                        histRecyclerView.setAdapter(histAdapter);

                        Button btn_out = dialog.findViewById(R.id.btn_out);

                        btn_out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        mealApi.gethistMeal(clicked).enqueue(new Callback<List<Meal_texthist>>() {
                            @Override
                            public void onResponse(Call<List<Meal_texthist>> call, Response<List<Meal_texthist>> response) {

                                Log.e("not loaded", response.body() + "======================================");
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        List<Meal_texthist> datas = response.body();
                                        if (datas != null && datas.size() > 1) {
                                            histArrayList.clear();
                                            histArrayList.addAll(datas); // Populate the correct list

                                            histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                            Log.e("get Hist success", datas + "======================================");

                                            histAdapter.setOnItemClickListener(new Meal_adapterhist.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View v, int pos) {
                                                    Meal_texthist hist = histArrayList.get(pos);

                                                    AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                                    View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.meal_detail_hist, null, false);
                                                    histBuilder.setView(detailDialog);

                                                    final AlertDialog hdialog = histBuilder.create();
                                                    hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    hdialog.show();

                                                    List<Meal_Image> images = meal_text.getImages();

                                                    final ImageView iv_meal_detail = hdialog.findViewById(R.id.iv_meal_detail);
                                                    final TextView tv_meal_detail = hdialog.findViewById(R.id.tv_meal_detail);

                                                    Glide.with(getContext()).load(images.get(0).getFilePath()).into(iv_meal_detail);

                                                    tv_meal_detail.setText(hist.getContent());

                                                    Button btn_off = hdialog.findViewById(R.id.btn_meal_detail);

                                                    btn_off.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            hdialog.dismiss();
                                                        }
                                                    });

                                                    mealApi.gethistMeal_detail(clicked, pos).enqueue(new Callback<List<Meal_texthist>>() {
                                                        @Override
                                                        public void onResponse(Call<List<Meal_texthist>> call, Response<List<Meal_texthist>> response) {
                                                            if (response.isSuccessful()) {
                                                                List<Meal_texthist> detail = response.body();
                                                                if (detail != null) {

                                                                    Log.e("Detail OK", detail + "------------");
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<Meal_texthist>> call, Throwable t) {
                                                            Log.e("Detail Fetch Failure", t.getMessage());
                                                        }
                                                    });

                                                }
                                            });

                                        } else {

                                            AlertDialog.Builder noHistBuilder = new AlertDialog.Builder(getContext());
                                            noHistBuilder.setMessage("수정된 기록이 없습니다.")
                                                    .setPositiveButton("확인", null)
                                                    .create()
                                                    .show();

                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Meal_texthist>> call, Throwable t) {
                                Log.e("Histlist Failure", t.getMessage() + "======================================");
                            }
                        });

                    }
                });
            }
        });
        return view;

    }

    public void getmeallsit(){

        Meal_API meal_service = Retrofit_client.createService(Meal_API.class, TokenUtils.getAccessToken("Access_Token"));

        meal_service.getDatameal(userid, puserid).enqueue(new Callback<List<Meal_ResponseDTO>>() {
            @Override
            public void onResponse(Call<List<Meal_ResponseDTO>> call, Response<List<Meal_ResponseDTO>> response) {
                if (response.body() != null) {
                    List<Meal_ResponseDTO> datas = response.body();

                    if (datas != null) {
                        mealArrayList.clear();
                        List<Meal_text_modified> tempList = new ArrayList<>();

                        for (int i = 0; i < datas.size(); i++) {

                            Long mealId = datas.get(i).getId();
                            int finalI = i;

                            meal_service.gethistMeal(mealId).enqueue(new Callback<List<Meal_texthist>>() {
                                @Override
                                public void onResponse(Call<List<Meal_texthist>> call, Response<List<Meal_texthist>> response) {
                                    List<Meal_texthist> histDatas = response.body();
                                    boolean isModified = (histDatas != null && histDatas.size() > 1);

                                    List<Meal_Image> images = datas.get(finalI).getImages();
                                    Log.e("pic!", datas.get(finalI).getImages().get(0).getFilePath() + "   ");


                                    Meal_text_modified modifiedItem = new Meal_text_modified(
                                            datas.get(finalI).getId(),
                                            datas.get(finalI).getUserId(),
                                            datas.get(finalI).getPuserId(),
                                            datas.get(finalI).getContent(),
                                            images,
                                            datas.get(finalI).getCreatedDateTime(),
                                            isModified
                                    );

                                    tempList.add(modifiedItem);

                                    if (tempList.size() == datas.size()) {
                                        Collections.sort(tempList, new Comparator<Meal_text_modified>() {
                                            @Override
                                            public int compare(Meal_text_modified item1, Meal_text_modified item2) {
                                                return item2.getCreatedDateTime().compareTo(item1.getCreatedDateTime());
                                            }
                                        });

                                        mealArrayList.clear();
                                        mealArrayList.addAll(tempList);

                                        mealAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Meal_texthist>> call, Throwable t) {

                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Meal_ResponseDTO>> call, Throwable t) {
                Log.e("통신에러","+"+t.toString());
                //Toast.makeText(getContext(), "통신에러", Toast.LENGTH_SHORT).show();
            }
        });

    }
}