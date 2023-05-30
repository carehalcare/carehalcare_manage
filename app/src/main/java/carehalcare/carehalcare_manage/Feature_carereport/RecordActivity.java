package carehalcare.carehalcare_manage.Feature_carereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_API;
import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_text;
import carehalcare.carehalcare_manage.Feature_carereport.Bowel.Bowel_API;
import carehalcare.carehalcare_manage.Feature_carereport.Bowel.Bowel_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Bowel.Bowel_text;
import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_API;
import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.MealFragment;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_API;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_API;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_text;
import carehalcare.carehalcare_manage.Feature_carereport.Sleep.Sleep_API;
import carehalcare.carehalcare_manage.Feature_carereport.Sleep.Sleep_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Sleep.Sleep_text;
import carehalcare.carehalcare_manage.Feature_carereport.Walk.Walk_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Walk.Walk_adapter;
import carehalcare.carehalcare_manage.Feature_carereport.Wash.Wash_API;
import carehalcare.carehalcare_manage.Feature_carereport.Wash.Wash_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Wash.Wash_adapter;
import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecordActivity extends AppCompatActivity {
    private FrameLayout container;

    LoadingDialog loadingDialog;
    Long ids;
    private String userid , puserid;

    private Medicine_adapter medicineAdapter;
    private Active_adapter activeAdapter;
    private Clean_adapter cleanAdapter;
    private Bowel_adapter bowelAdapter;
    private Wash_adapter washAdapter;
    private Sleep_adapter sleepAdapter;
    private Meal_adapter mealAdapter;
    private Walk_adapter walkAdapter;

    private ArrayList<Active_text> activeArrayList;
    private ArrayList<Clean_ResponseDTO> cleanArrayList;
    private ArrayList<Wash_ResponseDTO> washArrayList;
    private ArrayList<Sleep_text> sleepArrayList;
    private ArrayList<Bowel_text> bowelArrayList;
    private ArrayList<Medicine_text> medicineArrayList;
    private ArrayList<Meal_ResponseDTO> mealArrayList;
    private ArrayList<Walk_ResponseDTO> walkArrayList;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public void deleteview(){
        container.removeAllViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writemain);
        container = (FrameLayout) findViewById(R.id.container_menu);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid1");
        puserid = intent.getStringExtra("puserid1");

        loadingDialog = new LoadingDialog(this);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.setCancelable(false);
    }


    //투약정보
    public void onMedicine(View view) {
        deleteview();
        loadingDialog.show();
        Medicine_API medicineApi = retrofit.create(Medicine_API.class);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_layout,container,true);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        medicineArrayList = new ArrayList<>();
        medicineAdapter = new Medicine_adapter(medicineArrayList);
        mRecyclerView.setAdapter(medicineAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        medicineApi.getDatamedicine("userid1","puserid1").enqueue(new Callback<List<Medicine_text>>() {
            @Override
            public void onResponse(Call<List<Medicine_text>> call, Response<List<Medicine_text>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Medicine_text> datas = response.body();
                        if (datas != null) {
                            medicineArrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {
                                Medicine_text dict_0 = new Medicine_text(response.body().get(i).getCreatedDateTime(),
                                        response.body().get(i).gettime(),
                                        response.body().get(i).getmealStatus(), response.body().get(i).getmedicine(),
                                        response.body().get(i).getUserid(),
                                        response.body().get(i).getPuserid(),response.body().get(i).getId());
                                medicineArrayList.add(dict_0);
                                medicineAdapter.notifyItemInserted(0);
                                Log.e("현재id : " + i, datas.get(i).getmedicine()+" "+datas.get(i).getId() + ""+"어댑터카운터"+medicineAdapter.getItemCount());
                            }
                        }
                    }
                    else {

                        Log.e("medi 불러오기", "Status Code : " + response.code());

                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("서버 오류 메시지", errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    loadingDialog.dismiss();}
            }
            @Override
            public void onFailure(Call<List<Medicine_text>> call, Throwable t) {
                Log.e("medi get 실패", t.getMessage());
                loadingDialog.dismiss();
            }
        });

        //list detail click listener
        medicineAdapter.setOnItemClickListener (new Medicine_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Medicine_text medicine_text = medicineArrayList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                View view = LayoutInflater.from(RecordActivity.this)
                        .inflate(R.layout.medicine_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final Button btn_close = dialog.findViewById(R.id.btn_medicine_detail);
                final TextView tv_date = dialog.findViewById(R.id.tv_medicine_detail_date);
                final TextView tv_time = dialog.findViewById(R.id.tv_medicine_detail_time);
                final TextView tv_state = dialog.findViewById(R.id.tv_medicine_detail_state);
                final TextView tv_name = dialog.findViewById(R.id.tv_medicine_detail_name);

                String date = medicine_text.getCreatedDateTime();
                String newdate = DateUtils.formatDate(date);

                tv_date.setText(newdate);
                tv_time.setText(medicine_text.gettime());
                tv_state.setText(medicine_text.getmealStatus());
                tv_name.setText(medicine_text.getmedicine());

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    //Sclean
    public void onClean(View view) {
        deleteview();
        loadingDialog.show();
        Clean_API cleanApi = retrofit.create(Clean_API.class);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_layout,container,true);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        cleanArrayList = new ArrayList<>();
        cleanAdapter = new Clean_adapter(cleanArrayList);
        mRecyclerView.setAdapter(cleanAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        cleanApi.getDataClean("userid1","puserid1").enqueue(new Callback<List<Clean_ResponseDTO>>() {
            @Override
            public void onResponse(Call<List<Clean_ResponseDTO>> call, Response<List<Clean_ResponseDTO>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Clean_ResponseDTO> datas = response.body();
                        if (datas != null) {
                            cleanArrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {

                                Clean_ResponseDTO dict_0 = new Clean_ResponseDTO(
                                        datas.get(i).getCleanliness(), datas.get(i).getContent(),
                                        datas.get(i).getCreatedDateTime(), datas.get(i).getId(),
                                        datas.get(i).getPuserId(), datas.get(i).getUserId()
                                );
                                cleanArrayList.add(dict_0);
                                cleanAdapter.notifyItemInserted(0);
                                Log.e("현재id : " + i, datas.get(i).getCleanliness()+" "+datas.get(i).getId() + ""+"어댑터카운터"+cleanAdapter.getItemCount());

                            }
                            Log.e("getSleep success", "======================================");
                        }
                    }loadingDialog.dismiss();}
            }

            @Override
            public void onFailure(Call<List<Clean_ResponseDTO>> call, Throwable t) {
                Log.e("getSleep fail", "======================================");
                loadingDialog.dismiss();
            }
        });


        cleanAdapter.setOnItemClickListener (new Clean_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Clean_ResponseDTO detail_clean_text = cleanArrayList.get(position);

                String sheet_cloth_ventilation = detail_clean_text.getCleanliness();
                String content_detail = detail_clean_text.getContent();

                cleanApi.getDataClean("userid1", "puserid1").enqueue(new Callback<List<Clean_ResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<Clean_ResponseDTO>> call, Response<List<Clean_ResponseDTO>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Clean_ResponseDTO> datas = response.body();
                                if (datas != null) {
                                    ids = response.body().get(position).getId();
                                    Log.e("지금 position : ", position + "이고 DB ID는 : " + ids);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Clean_ResponseDTO>> call, Throwable t) {
                        Log.e("getSleep fail", "======================================");
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);

                View view = LayoutInflater.from(RecordActivity.this)
                        .inflate(R.layout.clean_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final TextView detail_sheet = dialog.findViewById(R.id.tv_cleandatail_sheet);
                final TextView detail_cloth = dialog.findViewById(R.id.tv_cleandatail_cloth);
                final TextView detail_ventilation = dialog.findViewById(R.id.tv_cleandatail_ventilation);
                final TextView et_detail_clean = dialog.findViewById(R.id.tv_cleandetail_et);

                if (sheet_cloth_ventilation != null && !sheet_cloth_ventilation.isEmpty()) {
                    if (sheet_cloth_ventilation.contains("시트변경완료")) {
                        detail_sheet.setText("시트변경완료");
                    } else {
                        detail_sheet.setText("해당사항 없음");
                    }

                    if (sheet_cloth_ventilation.contains("환의교체완료")) {
                        detail_cloth.setText("환의교체완료");
                    } else {
                        detail_cloth.setText("해당사항 없음");
                    }

                    if (sheet_cloth_ventilation.contains("환기완료")) {
                        detail_ventilation.setText("환기완료");
                    } else {
                        detail_ventilation.setText("해당사항 없음");
                    }
                } else {
                    detail_sheet.setText("해당사항 없음");
                    detail_cloth.setText("해당사항 없음");
                    detail_ventilation.setText("해당사항 없음");
                }
                if (content_detail.equals("-"))
                    et_detail_clean.setText("없음");
                else et_detail_clean.setText(content_detail);

                final Button btn_cleandetail = dialog.findViewById(R.id.btn_cleandtail);

                btn_cleandetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }



    //Pclean
    public void onWash(View view) {
        deleteview();
        loadingDialog.show();
        Wash_API washApi = retrofit.create(Wash_API.class);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_layout, container,true);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        washArrayList = new ArrayList<>();
        washAdapter = new Wash_adapter( washArrayList);
        mRecyclerView.setAdapter(washAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        washApi.getDataWash("userid1","puserid1").enqueue(new Callback<List<Wash_ResponseDTO>>() {
            @Override
            public void onResponse(Call<List<Wash_ResponseDTO>> call, Response<List<Wash_ResponseDTO>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        List<Wash_ResponseDTO> datas = response.body();
                        if (datas != null) {
                            washArrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {
                                Wash_ResponseDTO wash = new Wash_ResponseDTO( datas.get(i).getCleanliness(),
                                        datas.get(i).getContent(), datas.get(i).getCreatedDateTime(),
                                        datas.get(i).getId(),
                                        datas.get(i).getPart(), datas.get(i).getUserId(), datas.get(i).getPuserId());

                                washArrayList.add(wash);
                                washAdapter.notifyItemInserted(0);

                                Log.e("현재id : " + i, datas.get(i).getCleanliness() + " " + datas.get(i).getId() + "" + "어댑터카운터" + washAdapter.getItemCount());
                            }
                        }
                        else {

                            Log.e("pclean 불러오기", "Status Code : " + response.code());

                            try {
                                String errorBody = response.errorBody().string();
                                Log.e("서버 오류 메시지", errorBody);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    loadingDialog.dismiss();}
            }
            @Override
            public void onFailure(Call<List<Wash_ResponseDTO>> call, Throwable t) {
                loadingDialog.dismiss();
            }
        });

        washAdapter.setOnItemClickListener (new Wash_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Wash_ResponseDTO detail_wash_text = washArrayList.get(position);

                String cleantype = detail_wash_text.getCleanliness();
                String part = detail_wash_text.getPart();
                String content_detail = detail_wash_text.getContent();

                washApi.getDataWash("userid1","puserid1").enqueue(new Callback<List<Wash_ResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<Wash_ResponseDTO>> call, Response<List<Wash_ResponseDTO>> response) {
                        if(response.isSuccessful()){
                            if (response.body() != null) {
                                List<Wash_ResponseDTO> datas = response.body();
                                if (datas != null) {
                                    ids = response.body().get(position).getId();
                                    Log.e("지금 position : ",position+"이고 DB ID는 : " + ids);
                                }
                            }}
                    }
                    @Override
                    public void onFailure(Call<List<Wash_ResponseDTO>> call, Throwable t) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);

                View view = LayoutInflater.from(RecordActivity.this)
                        .inflate(R.layout.wash_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final TextView tv_face = dialog.findViewById(R.id.tv_washdetail_face);
                final TextView tv_mouth = dialog.findViewById(R.id.tv_washdetail_mouth);
                final TextView tv_nail = dialog.findViewById(R.id.tv_washdetail_nail);
                final TextView tv_hair  = dialog.findViewById(R.id.tv_washdetail_hair);
                final TextView tv_scrub  = dialog.findViewById(R.id.tv_washdetail_scrub);
                final TextView tv_shave  = dialog.findViewById(R.id.tv_washdetail_shave);
                final TextView tv_scrub_point  = dialog.findViewById(R.id.tv_washdetail_scrub_point);
                final TextView tv_et  = dialog.findViewById(R.id.tv_washdetail_et);
                final TextView tv_date = dialog.findViewById(R.id.tv_date);

                String date = detail_wash_text.getCreatedDateTime();
                String formattedDate = DateUtils.formatDate(date);

                tv_date.setText(formattedDate);
                tv_scrub_point.setText(part);

                if (content_detail.equals("-"))
                        tv_et.setText("없음");
                else tv_et.setText(content_detail);

                //조건
                if (cleantype != null && !cleantype.isEmpty()) {
                    if (cleantype.contains("세안 완료")) {
                        tv_face.setText("세안 완료");
                    } else {
                        tv_face.setText("해당사항 없음");
                    }

                    if (cleantype.contains("구강청결 완료")) {
                        tv_mouth.setText("구강청결 완료");
                    } else {
                        tv_mouth.setText("해당사항 없음");
                    }

                    if (cleantype.contains("손발톱관리 완료")) {
                        tv_nail.setText("손발톱관리 완료");
                    } else {
                        tv_nail.setText("해당사항 없음");
                    }

                    if (cleantype.contains("세발간호 완료")) {
                        tv_hair.setText("세발간호 완료");
                    } else {
                        tv_hair.setText("해당사항 없음");
                    }
                    if (cleantype.contains("세신 완료")) {
                        tv_scrub.setText("세신 완료");
                    } else {
                        tv_scrub.setText("해당사항 없음");
                    }
                    if (cleantype.contains("면도 완료")) {
                        tv_shave.setText("면도 완료");
                    } else {
                        tv_shave.setText("해당사항 없음");
                    }

                } else {
                    tv_face.setText("해당사항 없음"); tv_mouth.setText("해당사항 없음"); tv_nail.setText("해당사항 없음");
                    tv_hair.setText("해당사항 없음"); tv_scrub.setText("해당사항 없음"); tv_shave.setText("해당사항 없음");
                }

               final Button btn_washclose = dialog.findViewById(R.id.btn_wash_detail);

                btn_washclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    //Bowel
    public void onToilet(View view) {
        deleteview();
        loadingDialog.show();

        Bowel_API bowelApi = retrofit.create(Bowel_API.class);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_layout,container,true);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        bowelArrayList = new ArrayList<>();
        bowelAdapter = new Bowel_adapter(bowelArrayList);
        mRecyclerView.setAdapter(bowelAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        bowelApi.getDataBowel("userid1","puserid1").enqueue(new Callback<List<Bowel_text>>() {
            @Override
            public void onResponse(Call<List<Bowel_text>> call, Response<List<Bowel_text>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Bowel_text> datas = response.body();
                        if (datas != null) {
                            bowelArrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {
                                Bowel_text dict_0 = new Bowel_text(response.body().get(i).getCreatedDateTime(),
                                        response.body().get(i).getUserId(),
                                        response.body().get(i).getPuserId(),
                                        response.body().get(i).getId(),
                                        response.body().get(i).getCount(),response.body().get(i).getContent());
                                bowelArrayList.add(dict_0);
                                bowelAdapter.notifyItemInserted(0);
                                Log.e("현재id : " + i, datas.get(i).getCount()+" "+datas.get(i).getId() + ""+"어댑터카운터"+bowelAdapter.getItemCount());
                            }
                            Log.e("getSleep success", "======================================");
                        }
                    }loadingDialog.dismiss();}
            }
            @Override
            public void onFailure(Call<List<Bowel_text>> call, Throwable t) {
                Log.e("getSleep fail", "======================================");
                loadingDialog.dismiss();
            }
        });

        bowelAdapter.setOnItemClickListener (new Bowel_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Bowel_text detail_bowel_text = bowelArrayList.get(position);

                bowelApi.getDataBowel("userid1","puserid1").enqueue(new Callback<List<Bowel_text>>() {
                    @Override
                    public void onResponse(Call<List<Bowel_text>> call, Response<List<Bowel_text>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Bowel_text> datas = response.body();
                                if (datas != null) {
                                    ids = response.body().get(position).getId();
                                    Log.e("지금 position : ",position+"이고 DB ID는 : " + ids);
                                }
                            }}
                    }
                    @Override
                    public void onFailure(Call<List<Bowel_text>> call, Throwable t) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);

                View view = LayoutInflater.from(RecordActivity.this)
                        .inflate(R.layout.bowel_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final TextView boweldetail_count = dialog.findViewById(R.id.tv_boweldetail_count);
                final TextView boweldetail_et = dialog.findViewById(R.id.tv_boweldetail_et);
                final TextView tv_date = dialog.findViewById(R.id.tv_date);
                final Button btn_boweldetail = dialog.findViewById(R.id.btn_boweldetail);

                String date = detail_bowel_text.getCreatedDateTime();
                String newdate = DateUtils.formatDate(date);

                tv_date.setText(newdate);
                boweldetail_count.setText(String.valueOf(detail_bowel_text.getCount()) + "회");
                boweldetail_et.setText(detail_bowel_text.getContent());


                btn_boweldetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    //Active
    public void onActive(View view) {
        deleteview();
        loadingDialog.show();
        Active_API activeApi = retrofit.create(Active_API.class);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_layout,container,true);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        activeArrayList = new ArrayList<>();

        activeAdapter = new Active_adapter( activeArrayList);
        mRecyclerView.setAdapter(activeAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        activeApi.getDataActive("userid1","puserid1").enqueue(new Callback<List<Active_text>>() {
            @Override
            public void onResponse(Call<List<Active_text>> call, Response<List<Active_text>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Active_text> datas = response.body();
                        if (datas != null) {
                            activeArrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {
                                Active_text dict_0 = new Active_text(response.body().get(i).getCreatedDateTime(),
                                        response.body().get(i).getId(), response.body().get(i).getPosition(),
                                        response.body().get(i).getPuserId(), response.body().get(i).getRehabilitation(),
                                        response.body().get(i).getUserId(), response.body().get(i).getWalkingAssistance());
                                activeArrayList.add(dict_0);
                                activeAdapter.notifyItemInserted(0);
                                Log.e("현재id : " + i, datas.get(i).getPosition()+" "+datas.get(i).getId() + ""+"어댑터카운터"+activeAdapter.getItemCount());
                            }
                            Log.e("getActive success", "======================================");
                        }
                    }
                    loadingDialog.dismiss();}
            }
            @Override
            public void onFailure(Call<List<Active_text>> call, Throwable t) {
                loadingDialog.dismiss();
            }
        });

        activeAdapter.setOnItemClickListener (new Active_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Active_text detail_active_text = activeArrayList.get(position);
                activeApi.getDataActive("userid1","puserid1").enqueue(new Callback<List<Active_text>>() {
                    @Override
                    public void onResponse(Call<List<Active_text>> call, Response<List<Active_text>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Active_text> datas = response.body();
                                if (datas != null) {
                                    ids = response.body().get(position).getId();
                                    Log.e("지금 position : ",position+"이고 DB ID는 : " + ids);
                                }
                            }}
                    }
                    @Override
                    public void onFailure(Call<List<Active_text>> call, Throwable t) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                View view = LayoutInflater.from(RecordActivity.this)
                        .inflate(R.layout.active_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final TextView activedetail_jahal = dialog.findViewById(R.id.tv_activedetail_jahal);
                final TextView activedetail_bohang = dialog.findViewById(R.id.tv_activedetail_bohang);
                final TextView activedetail_change = dialog.findViewById(R.id.tv_activedetail_change);

                activedetail_jahal.setText(detail_active_text.getRehabilitation());
                activedetail_bohang.setText(detail_active_text.getWalkingAssistance());
                activedetail_change.setText(detail_active_text.getPosition());

                final Button btn_active_detail = dialog.findViewById(R.id.btn_active_detail);

                btn_active_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    //Sleep
    public void onSleep(View view) {
        deleteview();
        loadingDialog.show();

        Sleep_API sleepApi = retrofit.create(Sleep_API.class);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_layout,container,true);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        sleepArrayList = new ArrayList<>();
        sleepAdapter = new Sleep_adapter( sleepArrayList);
        mRecyclerView.setAdapter(sleepAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        sleepApi.getDataSleep("userid1","puserid1").enqueue(new Callback<List<Sleep_text>>() {
            @Override
            public void onResponse(Call<List<Sleep_text>> call, Response<List<Sleep_text>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Sleep_text> datas = response.body();
                        if (datas != null) {
                            sleepArrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {
                                Sleep_text dict_0 = new Sleep_text(
                                        datas.get(i).getContent(), datas.get(i).getCreatedDateTime(),
                                        datas.get(i).getId(), datas.get(i).getPuserId(),
                                        datas.get(i).getState(), datas.get(i).getUserId());
                                sleepArrayList.add(dict_0);
                                sleepAdapter.notifyItemInserted(0);
                                Log.e("현재id : " + i, datas.get(i).getState()+" "+datas.get(i).getId() + ""+"어댑터카운터"+sleepAdapter.getItemCount());
                            }
                            Log.e("getSleep success", "======================================");
                        }
                    }
                    loadingDialog.dismiss();}
            }
            @Override
            public void onFailure(Call<List<Sleep_text>> call, Throwable t) {
                Log.e("getSleep fail", "======================================");
                loadingDialog.dismiss();
            }


        });


        sleepAdapter.setOnItemClickListener (new Sleep_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Sleep_text detail_sleep_text = sleepArrayList.get(position);

                sleepApi.getDataSleep("userid1","puserid1").enqueue(new Callback<List<Sleep_text>>() {
                    @Override
                    public void onResponse(Call<List<Sleep_text>> call, Response<List<Sleep_text>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Sleep_text> datas = response.body();
                                if (datas != null) {
                                    ids = response.body().get(position).getId();
                                    Log.e("지금 position : ",position+"이고 DB ID는 : " + ids);
                                }
                            }}
                    }
                    @Override
                    public void onFailure(Call<List<Sleep_text>> call, Throwable t) {
                        Log.e("getSleep fail", "======================================");
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);

                View view = LayoutInflater.from(RecordActivity.this)
                        .inflate(R.layout.sleep_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final TextView tv_sleepdetail_state = dialog.findViewById(R.id.tv_sleepdetail_state);
                final TextView tv_sleepdetail_et = dialog.findViewById(R.id.tv_sleepdetail_et);

                tv_sleepdetail_state.setText(detail_sleep_text.getState());
                tv_sleepdetail_et.setText(detail_sleep_text.getContent());

                final Button btn_sleep_detail = dialog.findViewById(R.id.btn_sleep_detail);
                btn_sleep_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

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
}
