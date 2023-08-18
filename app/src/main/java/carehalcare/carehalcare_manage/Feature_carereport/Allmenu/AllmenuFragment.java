package carehalcare.carehalcare_manage.Feature_carereport.Allmenu;

import android.content.DialogInterface;
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
import com.google.gson.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import carehalcare.carehalcare_manage.Feature_mainpage.API_URL;
import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_API;
import carehalcare.carehalcare_manage.Feature_carereport.Active.Active_text;
import carehalcare.carehalcare_manage.Feature_carereport.Bowel.Bowel_API;
import carehalcare.carehalcare_manage.Feature_carereport.Bowel.Bowel_text;
import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_API;
import carehalcare.carehalcare_manage.Feature_carereport.Clean.Clean_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.DividerItemDecorator;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_API;
import carehalcare.carehalcare_manage.Feature_carereport.Meal.Meal_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_API;
import carehalcare.carehalcare_manage.Feature_carereport.Medicine.Medicine_text;
import carehalcare.carehalcare_manage.Feature_carereport.Sleep.Sleep_API;
import carehalcare.carehalcare_manage.Feature_carereport.Sleep.Sleep_text;
import carehalcare.carehalcare_manage.Feature_carereport.Walk.Walk_API;
import carehalcare.carehalcare_manage.Feature_carereport.Walk.Walk_ResponseDTO;
import carehalcare.carehalcare_manage.Feature_carereport.Wash.Wash_API;
import carehalcare.carehalcare_manage.Feature_carereport.Wash.Wash_ResponseDTO;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllmenuFragment extends Fragment {
    Active_text active_text;
    Bowel_text bowel_text;
    Medicine_text medicine_text;
    Sleep_text setText;
    String userid,puserid;
    Long ids;  //TODO ids는 삭제할 id값
    private ArrayList<BoardResponseDto> boardResponseDtoArrayList;
    private Allmenu_adapter allmenu_adapter;
    Gson gson = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context)
                    -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context)
                    -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context)
                    -> LocalTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("HH:mm:ss")))
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public AllmenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allmenu_list, container, false);
        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

        Allmenu_API allmenu_api = retrofit.create(Allmenu_API.class);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_allmenu_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        boardResponseDtoArrayList= new ArrayList<>();

        allmenu_adapter = new Allmenu_adapter(boardResponseDtoArrayList);
        mRecyclerView.setAdapter(allmenu_adapter);

        Log.e("uid",userid);
        Log.e("puid",puserid);


        allmenu_api.getallmenu("Bearer " + TokenUtils.getAccessToken("Access_Token")
                ,userid,puserid).enqueue(new Callback<List<BoardResponseDto>>() {
            @Override
            public void onResponse(Call<List<BoardResponseDto>> call, Response<List<BoardResponseDto>> response) {
                if (response.isSuccessful()) {
                    Log.e("제네릭타입","success");
                    Log.e("제네릭타입",response.body().size()+"");
                    if (response.body() != null) {
                        for (int i = 0; i < response.body().size(); i++) {
                            BoardResponseDto boardResponseDto = new BoardResponseDto(response.body().get(i).getId(),
                                    response.body().get(i).getUserId(),response.body().get(i).getPuserId(),
                                    response.body().get(i).getCategory(), response.body().get(i).getCreatedDateTime()
                            );
                            boardResponseDtoArrayList.add(boardResponseDto);
                            allmenu_adapter.notifyItemInserted(0);
                            Log.e("제네릭 구체적 : " ,response.body().get(i).getCategory()+"");
                            Log.e("제네릭 구체적 : " ,response.body().get(i).getCreatedDateTime()+"");

                        }
                    }
                } else{
                    Log.e("제네릭타입 실패","");
                }

            }

            @Override
            public void onFailure(Call<List<BoardResponseDto>>call, Throwable t) {
                Log.e("제네릭타입 fail",""+t.toString());

            }
        });

        allmenu_adapter.setOnItemClickListener(new Allmenu_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);
                switch (boardResponseDto.getCategory()){
                    case "activities":
                        showactive(position);
                        break;
                    case "administrations":
                        showmedicine(position);
                        break;
                    case "bowelmovements":
                        showwtoilet(position);
                        break;
                    case "meals":
                        showmeal(position);
                        break;
                    case "pcleanliness":
                        showwash(position);

                        break;
                    case "sleepstates":
                        showsleep(position);

                        break;
                    case "scleanliness":
                        showclean(position);

                        break;
                    case "walks":
                        showwalk(position);

                        break;
                    default: break;


                }
            }
        });

    return view;
    }

    public void showactive( int position){
        Active_API activeApi = Retrofit_client.createService(Active_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);
        Log.e("활동기록 position id????",boardResponseDto.getId()+"");

        activeApi.getDataActive_detail(boardResponseDto.getId()).enqueue(new Callback<Active_text>() {
            @Override
            public void onResponse(Call<Active_text> call, Response<Active_text> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Active_text datas = response.body();
                        if (datas != null) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.active_detail, null, false);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();

                            final TextView activedetail_jahal = dialog.findViewById(R.id.tv_activedetail_jahal);
                            final TextView activedetail_bohang = dialog.findViewById(R.id.tv_activedetail_bohang);
                            final TextView activedetail_change = dialog.findViewById(R.id.tv_activedetail_change);

                            String getjahal = datas.getRehabilitation();
                            String getbohang = datas.getWalkingAssistance();
                            String getchange = datas.getPosition();

                            if(getjahal.equals("Y"))
                                activedetail_jahal.setText("완료");
                            else activedetail_jahal.setText("-");

                            if(getbohang.equals("Y"))
                                activedetail_bohang.setText("완료");
                            else activedetail_bohang.setText("-");

                            if (getchange.equals("Y"))
                                activedetail_change.setText("완료");
                            else activedetail_change.setText("-");

                            final Button btn_active_detail = dialog.findViewById(R.id.btn_active_detail);
                            btn_active_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Active_text> call, Throwable t) {
            }
        });

    }
    public void showmedicine( int position){
        Medicine_API medicineApi = Retrofit_client.createService(Medicine_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);

        medicineApi.getDatamedicine_detail(boardResponseDto.getId()).enqueue(new Callback<Medicine_text>() {
            @Override
            public void onResponse(Call<Medicine_text> call, Response<Medicine_text> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Medicine_text datas = response.body();
                        if (datas != null) {
                            String gettime = datas.gettime();
                            String getmealStatus = datas.getmealStatus();
                            String getmedicine = datas.getmedicine();
                            String timedate = datas.getCreatedDateTime();
                            Log.e("투약기록????",gettime+getmealStatus+getmedicine);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.medicine_detail, null, false);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();

                            final TextView medicine_detail_timne = dialog.findViewById(R.id.tv_medicine_detail_time);
                            final TextView medicine_detail_state = dialog.findViewById(R.id.tv_medicine_detail_state);
                            final TextView medicine_detail_name = dialog.findViewById(R.id.tv_medicine_detail_name);

                            medicine_detail_timne.setText(gettime);
                            medicine_detail_state.setText(getmealStatus);
                            medicine_detail_name.setText(getmedicine);

                            final Button btn_medicinedetail = dialog.findViewById(R.id.btn_medicine_detail);
                            btn_medicinedetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Medicine_text> call, Throwable t) {
            }
        });}
    public void showwtoilet( int position){
        Bowel_API bowelApi = Retrofit_client.createService(Bowel_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);
        Log.e("활동기록 position id????",boardResponseDto.getId()+"");

        bowelApi.getDataBowel_detail(boardResponseDto.getId()).enqueue(new Callback<Bowel_text>() {
            @Override
            public void onResponse(Call<Bowel_text> call, Response<Bowel_text> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bowel_text datas = response.body();
                        if (datas != null) {
                            Long getCount = datas.getCount();
                            String getContent = datas.getContent();

                            Log.e("배변기록????",getContent+getCount);

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.bowel_detail, null, false);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();

                            final TextView boweldetail_count = dialog.findViewById(R.id.tv_boweldetail_count);
                            final TextView boweldetail_et = dialog.findViewById(R.id.tv_boweldetail_et);

                            boweldetail_count.setText(String.valueOf(getCount));
                            boweldetail_et.setText(getContent);

                            final Button btn_boweldetail = dialog.findViewById(R.id.btn_boweldetail);
                            btn_boweldetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Bowel_text> call, Throwable t) {
            }
        });
    }
    public void showwash( int position){
        Wash_API washApi = Retrofit_client.createService(Wash_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);

        washApi.getDataWash_detail(boardResponseDto.getId()).enqueue(new Callback<Wash_ResponseDTO>() {
            @Override
            public void onResponse(Call<Wash_ResponseDTO> call, Response<Wash_ResponseDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Wash_ResponseDTO datas = response.body();
                        if (datas != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.allmenu_wash_detail, null, false);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();

                            final TextView tv_washdetail_allmenu = dialog.findViewById(R.id.tv_washdetail_allmenu);
                            final TextView tv_washallmenu_et = dialog.findViewById(R.id.tv_washallmenu_et);
                            final TextView tv_washpart_allmenu = dialog.findViewById(R.id.tv_washpart_allmenu);

                            String cleanvalues[] = datas.getCleanliness().split(" ");
                            String getContent = datas.getContent();
                            String getPart = datas.getPart();

                            String str_clean = "";
                            for (int i = 0; i < cleanvalues.length; i++) {
                                if (cleanvalues[i].equals("Y")) {
                                    switch (i) {
                                        case 0:
                                            str_clean += "세안";
                                            break;
                                        case 1:
                                            str_clean += "구강청결";
                                            break;
                                        case 2:
                                            str_clean += "손발톱관리";
                                            break;
                                        case 3:
                                            str_clean += "세발간호";
                                            break;
                                        case 4:
                                            str_clean += "세신";
                                            break;
                                        case 5:
                                            str_clean += "면도";
                                            break;
                                    }

                                    // 다음 요소가 있는 경우에만 콤마 추가
                                    if (i < cleanvalues.length - 1) {
                                        boolean nextNotEmpty = false;
                                        for (int j = i + 1; j < cleanvalues.length; j++) {
                                            if (cleanvalues[j].equals("Y")) {
                                                nextNotEmpty = true;
                                                break;
                                            }
                                        }

                                        if (nextNotEmpty) {
                                            str_clean += ", ";
                                        }
                                    }
                                }
                            }

                            tv_washdetail_allmenu.setText(str_clean +"완료");
                            tv_washallmenu_et.setText(getContent);
                            tv_washpart_allmenu.setText(getPart);


                            final Button btn_washdetail = dialog.findViewById(R.id.btn_wash_detail_allmenu);
                            btn_washdetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Wash_ResponseDTO> call, Throwable t) {
            }
        });
    }
    public void showsleep( int position){
        Sleep_API sleepApi = Retrofit_client.createService(Sleep_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);

        sleepApi.getDataSleep_2(boardResponseDto.getId()).enqueue(new Callback<Sleep_text>() {
            @Override
            public void onResponse(Call<Sleep_text> call, Response<Sleep_text> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Sleep_text datas = response.body();
                        if (datas != null) {
                            String getState = datas.getState();
                            String getContent = datas.getContent();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.sleep_detail, null, false);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();

                            final TextView tv_sleepdetail_state = dialog.findViewById(R.id.tv_sleepdetail_state);
                            final TextView tv_sleepdetail_et = dialog.findViewById(R.id.tv_sleepdetail_et);

                            tv_sleepdetail_state.setText(getState);
                            tv_sleepdetail_et.setText(getContent);

                            final Button btn_sleep_detail = dialog.findViewById(R.id.btn_sleep_detail);

                            btn_sleep_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Sleep_text> call, Throwable t) {
            }
        });
    }
    public void showclean(int position){
        Clean_API cleanApi = Retrofit_client.createService(Clean_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);

        cleanApi.getDataClean_detail(boardResponseDto.getId()).enqueue(new Callback<Clean_ResponseDTO>() {
            @Override
            public void onResponse(Call<Clean_ResponseDTO> call, Response<Clean_ResponseDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Clean_ResponseDTO datas = response.body();
                        if (datas != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.allmenu_clean_detail, null, false);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();

                            final TextView tv_cleandatail_allmenu = dialog.findViewById(R.id.tv_cleandatail_allmenu);
                            final TextView tv_cleandetail_et_allmenu = dialog.findViewById(R.id.tv_cleandetail_et_allmenu);

                            String cleanvalues[] = datas.getCleanliness().split(" ");
                            String getContent = datas.getContent();

                            String str_clean = "";

                            for (int i = 0; i < cleanvalues.length; i++) {
                                if (cleanvalues[i].equals("Y")) {
                                    switch (i) {
                                        case 0:
                                            str_clean += "시트교체";
                                            break;
                                        case 1:
                                            str_clean += "환의교체";
                                            break;
                                        case 2:
                                            str_clean += "환기";
                                            break;
                                    }
                                    // 다음 요소가 있는 경우에만 콤마 추가
                                    if (i < cleanvalues.length - 1) {
                                        boolean nextNotEmpty = false;
                                        for (int j = i + 1; j < cleanvalues.length; j++) {
                                            if (cleanvalues[j].equals("Y")) {
                                                nextNotEmpty = true;
                                                break;
                                            }
                                        }

                                        if (nextNotEmpty) {
                                            str_clean += ", ";
                                        }
                                    }
                                }
                            }

                            tv_cleandatail_allmenu.setText(str_clean +"완료");
                            tv_cleandetail_et_allmenu.setText(getContent);

                            final Button btn_cleandetail = dialog.findViewById(R.id.btn_cleandtail);

                            btn_cleandetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Clean_ResponseDTO> call, Throwable t) {
            }
        });

    }
    public void showwalk(int position){
        Walk_API walkApi = Retrofit_client.createService(Walk_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);

        walkApi.getdatawalk2(boardResponseDto.getId()).enqueue(new Callback<Walk_ResponseDTO>() {
            @Override
            public void onResponse(Call<Walk_ResponseDTO> call, Response<Walk_ResponseDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Walk_ResponseDTO datas = response.body();
                        if (datas != null) {
                            String getFilepath = datas.getImages().get(0).getFilePath();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            View view = LayoutInflater.from(getContext())
                                    .inflate(R.layout.walk_detail, null, false);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();

                            final ImageView iv_walk_detail = dialog.findViewById(R.id.iv_walk_detail);
                            Glide.with(getContext()).load(getFilepath).into(iv_walk_detail);

                            final Button btn_walk_detail = dialog.findViewById(R.id.btn_walk_detail);

                            btn_walk_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Walk_ResponseDTO> call, Throwable t) {
            }
        });
    }
    public void showmeal( int position){
        Meal_API mealApi = Retrofit_client.createService(Meal_API.class,TokenUtils.getAccessToken("Access_Token"));
        BoardResponseDto boardResponseDto = boardResponseDtoArrayList.get(position);

        mealApi.getDatameal_detail(boardResponseDto.getId()).enqueue(new Callback<Meal_ResponseDTO>() {
            @Override
            public void onResponse(Call<Meal_ResponseDTO> call, Response<Meal_ResponseDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Meal_ResponseDTO datas = response.body();
                        if (datas != null) {
                            String getFilepath = datas.getImages().get(0).getFilePath();
                            String getContent = datas.getContent();

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
                            Glide.with(getContext()).load(getFilepath).into(iv_meal_detail);
                            tv_meal_detail.setText(getContent);

                            final Button btn_meal_detail = dialog.findViewById(R.id.btn_meal_detail);

                            btn_meal_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }}
            }
            @Override
            public void onFailure(Call<Meal_ResponseDTO> call, Throwable t) {
            }
        });

    }
}