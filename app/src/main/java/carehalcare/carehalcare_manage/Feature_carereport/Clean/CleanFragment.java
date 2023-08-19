package carehalcare.carehalcare_manage.Feature_carereport.Clean;

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

public class CleanFragment extends Fragment {
    String userid,puserid;
    Long ids;
    private ArrayList<Clean_text_modified> cleanArrayList;
    private Clean_adapter cleanAdapter;
    private ArrayList<Clean_texthist> histArrayList;
    private Clean_adapterhist histAdapter;

    private TextView detail_sheet, detail_cloth, detail_ventilation, et_detail_clean;
    private Button btn_off, btn_update;

    public CleanFragment() {
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

        Clean_API cleanApi = Retrofit_client.createService(Clean_API.class, TokenUtils.getAccessToken("Access_Token"));

        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        cleanArrayList = new ArrayList<>();
        cleanAdapter = new Clean_adapter(cleanArrayList);
        mRecyclerView.setAdapter(cleanAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        cleanApi.getDataClean(userid, puserid).enqueue(new Callback<List<Clean_ResponseDTO>>() {
            @Override
            public void onResponse(Call<List<Clean_ResponseDTO>> call, Response<List<Clean_ResponseDTO>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Clean_ResponseDTO> datas = response.body();
                        if (datas != null) {
                            cleanArrayList.clear();
                            List<Clean_text_modified> tempList = new ArrayList<>();

                            for (int i = 0; i < datas.size(); i++) {
                                Long activeId = datas.get(i).getId();
                                int finalI = i;

                                cleanApi.gethistSclean(activeId).enqueue(new Callback<List<Clean_texthist>>() {
                                    @Override
                                    public void onResponse(Call<List<Clean_texthist>> call, Response<List<Clean_texthist>> response) {
                                        if (response.isSuccessful()) {
                                            List<Clean_texthist> histDatas = response.body();
                                            boolean isModified = (histDatas != null && histDatas.size() > 1);

                                            Clean_text_modified modifiedItem = new Clean_text_modified(
                                                    datas.get(finalI).getId(),
                                                    datas.get(finalI).getUserId(),
                                                    datas.get(finalI).getPuserId(),
                                                    datas.get(finalI).getCleanliness(),
                                                    datas.get(finalI).getContent(),
                                                    datas.get(finalI).getCreatedDateTime(),
                                                    isModified
                                            );

                                            tempList.add(modifiedItem);

                                            if (tempList.size() == datas.size()) {
                                                Collections.sort(tempList, new Comparator<Clean_text_modified>() {
                                                    @Override
                                                    public int compare(Clean_text_modified item1, Clean_text_modified item2) {
                                                        return item2.getCreatedDateTime().compareTo(item1.getCreatedDateTime());
                                                    }
                                                });

                                                cleanArrayList.clear();
                                                cleanArrayList.addAll(tempList);

                                                cleanAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Clean_texthist>> call, Throwable t) {
                                        // 처리 실패 시의 로직
                                    }
                                });
                            }

                            Log.e("get success", "======================================");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Clean_ResponseDTO>> call, Throwable t) {
                // 실패 시의 처리 로직
            }
        });


        cleanAdapter.setOnItemClickListener (new Clean_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Clean_ResponseDTO detail_clean_text = cleanArrayList.get(position);
                Long clicked = detail_clean_text.getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.clean_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                detail_sheet = dialog.findViewById(R.id.tv_cleandetail_sheet);
                detail_cloth = dialog.findViewById(R.id.tv_cleandetail_cloth);
                detail_ventilation = dialog.findViewById(R.id.tv_cleandetail_ventilation);
                et_detail_clean = dialog.findViewById(R.id.tv_cleandetail_et);

                String cleandata[] = detail_clean_text.getCleanliness().split(" ");

                if (cleandata[0].equals("Y")) detail_sheet.setText("완료");
                else detail_sheet.setText("-");

                if (cleandata[1].equals("Y")) detail_cloth.setText("완료");
                else detail_cloth.setText("-");

                if (cleandata[2].equals("Y")) detail_ventilation.setText("완료");
                else detail_ventilation.setText("-");

                et_detail_clean.setText(detail_clean_text.getContent());

                btn_update = dialog.findViewById(R.id.btn_update_report);
                btn_off = dialog.findViewById(R.id.btn_cleandetail);

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

                        histArrayList = new ArrayList<>();
                        histAdapter = new Clean_adapterhist(histArrayList);
                        histRecyclerView.setAdapter(histAdapter);

                        Button btn_out = dialog.findViewById(R.id.btn_out);

                        btn_out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        cleanApi.gethistSclean(clicked).enqueue(new Callback<List<Clean_texthist>>() {
                            @Override
                            public void onResponse(Call<List<Clean_texthist>> call, Response<List<Clean_texthist>> response) {

                                Log.e("not loaded", response.body() + "======================================");
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        List<Clean_texthist> datas = response.body();
                                        if (datas != null && datas.size() > 1) {
                                            histArrayList.clear();
                                            histArrayList.addAll(datas); // Populate the correct list

                                            histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                            Log.e("get Hist success", datas + "======================================");

                                            histAdapter.setOnItemClickListener(new Clean_adapterhist.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View v, int pos) {
                                                    Clean_texthist hist = histArrayList.get(pos);

                                                    AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                                    View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.clean_detail_hist, null, false);
                                                    histBuilder.setView(detailDialog);

                                                    final AlertDialog hdialog = histBuilder.create();
                                                    hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    hdialog.show();

                                                    detail_sheet = hdialog.findViewById(R.id.tv_cleandetail_sheet);
                                                    detail_cloth = hdialog.findViewById(R.id.tv_cleandetail_cloth);
                                                    detail_ventilation = hdialog.findViewById(R.id.tv_cleandetail_ventilation);
                                                    et_detail_clean = hdialog.findViewById(R.id.tv_cleandetail_et);

                                                    String cleandata[] = hist.getCleanliness().split(" ");

                                                    if (cleandata[0].equals("Y"))
                                                        detail_sheet.setText("완료");
                                                    else detail_sheet.setText("-");

                                                    if (cleandata[1].equals("Y"))
                                                        detail_cloth.setText("완료");
                                                    else detail_cloth.setText("-");

                                                    if (cleandata[2].equals("Y"))
                                                        detail_ventilation.setText("완료");
                                                    else detail_ventilation.setText("-");

                                                    et_detail_clean.setText(hist.getContent());

                                                    btn_off = hdialog.findViewById(R.id.btn_cleandetail);

                                                    btn_off.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            hdialog.dismiss();
                                                        }
                                                    });

                                                    cleanApi.gethistSclean_detail(clicked, pos).enqueue(new Callback<List<Clean_texthist>>() {
                                                        @Override
                                                        public void onResponse(Call<List<Clean_texthist>> call, Response<List<Clean_texthist>> response) {
                                                            if (response.isSuccessful()) {
                                                                List<Clean_texthist> detail = response.body();
                                                                if (detail != null) {

                                                                    Log.e("Detail OK", detail + "------------");
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<Clean_texthist>> call, Throwable t) {
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
                            public void onFailure(Call<List<Clean_texthist>> call, Throwable t) {
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