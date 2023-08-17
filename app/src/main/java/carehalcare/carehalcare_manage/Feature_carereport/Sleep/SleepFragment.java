package carehalcare.carehalcare_manage.Feature_carereport.Sleep;

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

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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

public class SleepFragment extends Fragment {
    String userid,puserid;
    private ArrayList<Sleep_text> sleepArrayList;
    private Sleep_adapter SleepAdapter;

    private ArrayList<Sleep_texthist> histArrayList;
    private Sleep_adapterhist histAdapter;

    public SleepFragment() {
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

        Sleep_API sleepApi = Retrofit_client.createService(Sleep_API.class, TokenUtils.getAccessToken("Access_Token"));

        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        sleepArrayList = new ArrayList<>();
        SleepAdapter = new Sleep_adapter(sleepArrayList);
        mRecyclerView.setAdapter(SleepAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        sleepApi.getDataSleep(userid,puserid).enqueue(new Callback<List<Sleep_text>>() {
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
                                SleepAdapter.notifyItemInserted(0);
                                Log.e("현재id : " + i, datas.get(i).getState()+" "+datas.get(i).getId() + ""+"어댑터카운터"+ SleepAdapter.getItemCount());
                            }
                            Log.e("getSleep success", "======================================");
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Sleep_text>> call, Throwable t) {
                Log.e("getSleep fail", "======================================");

            }


        });


        SleepAdapter.setOnItemClickListener (new Sleep_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Sleep_text detail_Sleep_text = sleepArrayList.get(position);
                Long clicked = detail_Sleep_text.getId();

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

                histArrayList = new ArrayList<>();
                histAdapter = new Sleep_adapterhist(histArrayList);
                histRecyclerView.setAdapter(histAdapter);

                Button btn_out = dialog.findViewById(R.id.btn_out);

                btn_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                sleepApi.gethistSleep(clicked).enqueue(new Callback<List<Sleep_texthist>>() {
                    @Override
                    public void onResponse(Call<List<Sleep_texthist>> call, Response<List<Sleep_texthist>> response) {

                        Log.e("not loaded", response.body() + "======================================");
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Sleep_texthist> datas = response.body();
                                if (datas != null) {
                                    histArrayList.clear();
                                    histArrayList.addAll(datas); // Populate the correct list

                                    histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                    Log.e("get Hist success", datas+ "======================================");

                                    histAdapter.setOnItemClickListener(new Sleep_adapterhist.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int pos) {
                                            Sleep_texthist hist = histArrayList.get(pos);

                                            AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                            View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.sleep_detail, null, false);
                                            histBuilder.setView(detailDialog);

                                            final AlertDialog hdialog = histBuilder.create();
                                            hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            hdialog.show();

                                            final TextView tv_sleepdetail_state = hdialog.findViewById(R.id.tv_sleepdetail_state);
                                            final TextView tv_sleepdetail_et = hdialog.findViewById(R.id.tv_sleepdetail_et);

                                            tv_sleepdetail_state.setText(hist.getState());
                                            tv_sleepdetail_et.setText(hist.getContent());

                                            final Button btn_sleep_detail = hdialog.findViewById(R.id.btn_sleep_detail);
                                            btn_sleep_detail.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    hdialog.dismiss();
                                                }
                                            });

                                            sleepApi.gethistSleep_detail(clicked, pos).enqueue(new Callback<List<Sleep_texthist>>() {
                                                @Override
                                                public void onResponse(Call<List<Sleep_texthist>> call, Response<List<Sleep_texthist>> response) {
                                                    if (response.isSuccessful()) {
                                                        List<Sleep_texthist> detail = response.body();
                                                        if (detail != null) {

                                                            Log.e("Detail OK", detail + "------------");
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<Sleep_texthist>> call, Throwable t) {
                                                    Log.e("Detail Fetch Failure", t.getMessage());
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
                    public void onFailure(Call<List<Sleep_texthist>> call, Throwable t) {
                        Log.e("Histlist Failure", t.getMessage() + "======================================");
                    }
                });

            }
        });
        return view;
    }
}