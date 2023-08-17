package carehalcare.carehalcare_manage.Feature_carereport.Medicine;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.DateUtils;
import carehalcare.carehalcare_manage.Feature_carereport.DividerItemDecorator;
import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.Retrofit_client;
import carehalcare.carehalcare_manage.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineFragment extends Fragment {
    String userid,puserid;
    private ArrayList<Medicine_text> medicineArrayList;
    private Medicine_adapter medicineAdapter;

    private ArrayList<Medicine_texthist> histArrayList;
    private Medicine_adapterhist histAdapter;

    public MedicineFragment() {
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

        Medicine_API medicineApi = Retrofit_client.createService(Medicine_API.class, TokenUtils.getAccessToken("Access_Token"));

        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        medicineArrayList = new ArrayList<>();
        medicineAdapter = new Medicine_adapter( medicineArrayList);
        mRecyclerView.setAdapter(medicineAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        medicineApi.getDatamedicine(userid,puserid).enqueue(new Callback<List<Medicine_text>>() {
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
                }
            }
            @Override
            public void onFailure(Call<List<Medicine_text>> call, Throwable t) {
                Log.e("medi get 실패", t.getMessage());
            }
        });


        medicineAdapter.setOnItemClickListener (new Medicine_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Medicine_text detail_medicine_text = medicineArrayList.get(position);
                Long clicked = detail_medicine_text.getId();

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

                // Create and set medicine_adapterhist adapter for the histRecyclerView
                histArrayList = new ArrayList<>();
                histAdapter = new Medicine_adapterhist(histArrayList);
                histRecyclerView.setAdapter(histAdapter);

                Button btn_out = dialog.findViewById(R.id.btn_out);

                btn_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                medicineApi.gethistMedi(clicked).enqueue(new Callback<List<Medicine_texthist>>() {
                    @Override
                    public void onResponse(Call<List<Medicine_texthist>> call, Response<List<Medicine_texthist>> response) {

                        Log.e("not loaded", response.body() + "======================================");
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Medicine_texthist> datas = response.body();
                                if (datas != null) {
                                    histArrayList.clear();
                                    histArrayList.addAll(datas); // Populate the correct list

                                    histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                    Log.e("get Hist success", datas+ "======================================");

                                    histAdapter.setOnItemClickListener(new Medicine_adapterhist.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int pos) {
                                            Medicine_texthist hist = histArrayList.get(pos);

                                            AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                            View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.medicine_detail, null, false);
                                            histBuilder.setView(detailDialog);

                                            final AlertDialog hdialog = histBuilder.create();
                                            hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            hdialog.show();

                                            final Button btn_close = hdialog.findViewById(R.id.btn_medicine_detail);
                                            final TextView tv_time = hdialog.findViewById(R.id.tv_medicine_detail_time);
                                            final TextView tv_state = hdialog.findViewById(R.id.tv_medicine_detail_state);
                                            final TextView tv_name = hdialog.findViewById(R.id.tv_medicine_detail_name);

                                            tv_time.setText(hist.getTime());
                                            tv_state.setText(hist.getMealStatus());
                                            tv_name.setText(hist.getMedicine());

                                            btn_close.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    hdialog.dismiss();
                                                }
                                            });

                                            medicineApi.gethistMedi_detail(clicked, pos).enqueue(new Callback<List<Medicine_texthist>>() {
                                                @Override
                                                public void onResponse(Call<List<Medicine_texthist>> call, Response<List<Medicine_texthist>> response) {
                                                    if (response.isSuccessful()) {
                                                        List<Medicine_texthist> detail = response.body();
                                                        if (detail != null) {

                                                            Log.e("Detail OK", detail + "------------");
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<Medicine_texthist>> call, Throwable t) {
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
                    public void onFailure(Call<List<Medicine_texthist>> call, Throwable t) {
                        Log.e("Histlist Failure", t.getMessage() + "======================================");
                    }
                });

            }
        });
        return view;
    }
}