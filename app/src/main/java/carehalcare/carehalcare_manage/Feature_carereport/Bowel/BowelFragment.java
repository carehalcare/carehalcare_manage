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

    private ArrayList<Bowel_text> bowelArrayList;
    private ArrayList<Bowel_texthist> histArrayList;
    private Bowel_adapter bowelAdapter;
    private Bowel_adapterhist histAdapter;

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
                    }}
            }
            @Override
            public void onFailure(Call<List<Bowel_text>> call, Throwable t) {
                Log.e("getSleep fail", "======================================");
            }
        });

        bowelAdapter.setOnItemClickListener (new Bowel_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Bowel_text detail_bowel_text = bowelArrayList.get(position);
                Long clicked = detail_bowel_text.getId();

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
                                if (datas != null) {
                                    histArrayList.clear();
                                    histArrayList.addAll(datas); // Populate the correct list

                                    histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                    Log.e("get Hist success", datas+ "======================================");

                                    histAdapter.setOnItemClickListener(new Bowel_adapterhist.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int pos) {
                                            Bowel_texthist hist = histArrayList.get(pos);

                                            AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                            View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.bowel_detail, null, false);
                                            histBuilder.setView(detailDialog);

                                            final AlertDialog hdialog = histBuilder.create();
                                            hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            hdialog.show();

                                            TextView tv_count = hdialog.findViewById(R.id.tv_boweldetail_count);
                                            TextView tv_et = hdialog.findViewById(R.id.tv_boweldetail_et);

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
                                    Log.e("not loaded", datas.size() + "======================================");
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
        return view;
    }
}