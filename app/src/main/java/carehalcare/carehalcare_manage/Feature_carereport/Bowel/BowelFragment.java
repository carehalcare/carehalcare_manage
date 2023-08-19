package carehalcare.carehalcare_manage.Feature_carereport.Bowel;

import android.content.Context;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.DividerItemDecorator;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BowelFragment extends Fragment {
    String userid,puserid;

    Long ids;
    private ArrayList<Bowel_text_modified> bowelArrayList;
    private ArrayList<Bowel_texthist> histArrayList;
    private Bowel_adapter bowelAdapter;
    private Bowel_adapterhist histAdapter;

    private TextView tv_count, tv_et;
    private Button btn_off, btn_update;

    public BowelFragment() {
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

        //        Bowel_API bowelApi = retrofit.create(Bowel_API.class);
        Bowel_API bowelApi = Retrofit_client.createService(Bowel_API.class, TokenUtils.getAccessToken("Access_Token"));


        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        bowelArrayList = new ArrayList<>();
        bowelAdapter = new Bowel_adapter(bowelArrayList);
        mRecyclerView.setAdapter(bowelAdapter);


        bowelApi.getDataBowel(userid,puserid).enqueue(new Callback<List<Bowel_text>>() {
            @Override
            public void onResponse(Call<List<Bowel_text>> call, Response<List<Bowel_text>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Bowel_text> datas = response.body();
                        if (datas != null) {
                            bowelArrayList.clear();
                            List<Bowel_text_modified> tempList = new ArrayList<>();

                            for (int i = 0; i < datas.size(); i++) {
                                Long BowelId = datas.get(i).getId();
                                int finalI = i;

                                bowelApi.gethistBowel(BowelId).enqueue(new Callback<List<Bowel_texthist>>() {
                                    @Override
                                    public void onResponse(Call<List<Bowel_texthist>> call, Response<List<Bowel_texthist>> response) {
                                        if (response.isSuccessful()) {
                                            List<Bowel_texthist> histDatas = response.body();
                                            boolean isModified = (histDatas != null && histDatas.size() > 1);

                                            Bowel_text_modified modifiedItem = new Bowel_text_modified(
                                                    datas.get(finalI).getId(),
                                                    datas.get(finalI).getUserId(),
                                                    datas.get(finalI).getPuserId(),
                                                    datas.get(finalI).getCount(),
                                                    datas.get(finalI).getContent(),
                                                    datas.get(finalI).getCreatedDateTime(),
                                                    isModified
                                            );

                                            tempList.add(modifiedItem);

                                            if (tempList.size() == datas.size()) {
                                                Collections.sort(tempList, new Comparator<Bowel_text_modified>() {
                                                    @Override
                                                    public int compare(Bowel_text_modified item1, Bowel_text_modified item2) {
                                                        return item2.getCreatedDateTime().compareTo(item1.getCreatedDateTime());
                                                    }
                                                });

                                                bowelArrayList.clear();
                                                bowelArrayList.addAll(tempList);

                                                bowelAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Bowel_texthist>> call, Throwable t) {
                                        // 처리 실패 시의 로직
                                    }
                                });
                            }

                            Log.e("getBowel success", "======================================");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Bowel_text>> call, Throwable t) {
                // 실패 시의 처리 로직
            }
        });

        bowelAdapter.setOnItemClickListener (new Bowel_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Bowel_text detail_bowel_text = bowelArrayList.get(position);
                Long clicked = detail_bowel_text.getId();

                bowelApi.getDataBowel(userid, puserid).enqueue(new Callback<List<Bowel_text>>() {
                    @Override
                    public void onResponse(Call<List<Bowel_text>> call, Response<List<Bowel_text>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Bowel_text> datas = response.body();
                                if (datas != null) {
                                    ids = response.body().get(position).getId();
                                    Log.e("지금 position : ", position + "이고 DB ID는 : " + ids);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Bowel_text>> call, Throwable t) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.bowel_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                tv_count = dialog.findViewById(R.id.tv_boweldetail_count);
                tv_et = dialog.findViewById(R.id.tv_boweldetail_et);
                btn_off = dialog.findViewById(R.id.btn_boweldetail);
                btn_update = dialog.findViewById(R.id.btn_update_report);

                tv_count.setText(String.valueOf(detail_bowel_text.getCount()) + "회");
                tv_et.setText(detail_bowel_text.getContent());

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

                        // Create and set Bowel_adapterhist adapter for the histRecyclerView
                        histArrayList = new ArrayList<>();
                        histAdapter = new Bowel_adapterhist(histArrayList);
                        histRecyclerView.setAdapter(histAdapter);

                        Button btn_out = dialog.findViewById(R.id.btn_out);

                        btn_out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });


                        bowelApi.gethistBowel(clicked).enqueue(new Callback<List<Bowel_texthist>>() {
                            @Override
                            public void onResponse(Call<List<Bowel_texthist>> call, Response<List<Bowel_texthist>> response) {

                                Log.e("not loaded", response.body() + "======================================");
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        List<Bowel_texthist> datas = response.body();
                                        if (datas != null && datas.size() > 1) {
                                            histArrayList.clear();
                                            histArrayList.addAll(datas); // Populate the correct list

                                            histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                            Log.e("get Hist success", datas + "======================================");

                                            histAdapter.setOnItemClickListener(new Bowel_adapterhist.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View v, int pos) {
                                                    Bowel_texthist hist = histArrayList.get(pos);

                                                    AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                                    View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.bowel_detail_hist, null, false);
                                                    histBuilder.setView(detailDialog);

                                                    final AlertDialog hdialog = histBuilder.create();
                                                    hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    hdialog.show();

                                                    tv_count = hdialog.findViewById(R.id.tv_boweldetail_count);
                                                    tv_et = hdialog.findViewById(R.id.tv_boweldetail_et);

                                                    tv_count.setText(hist.getCount().toString());
                                                    tv_et.setText(hist.getContent());

                                                    bowelApi.gethistBowel_detail(clicked, pos).enqueue(new Callback<List<Bowel_texthist>>() {
                                                        @Override
                                                        public void onResponse(Call<List<Bowel_texthist>> call, Response<List<Bowel_texthist>> response) {
                                                            if (response.isSuccessful()) {
                                                                List<Bowel_texthist> detail = response.body();
                                                                if (detail != null) {

                                                                    Log.e("Detail OK", detail + "------------");
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<Bowel_texthist>> call, Throwable t) {
                                                            Log.e("Detail Fetch Failure", t.getMessage());
                                                        }
                                                    });

                                                    Button btn_off = hdialog.findViewById(R.id.btn_boweldetail);

                                                    btn_off.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            hdialog.dismiss();
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
                            public void onFailure(Call<List<Bowel_texthist>> call, Throwable t) {
                                Log.e("Histlist Failure", t.getMessage() + "======================================");
                            }
                        });
                    }
                });
            }
        });
        return view;
    }
}