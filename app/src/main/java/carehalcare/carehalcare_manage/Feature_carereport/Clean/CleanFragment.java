package carehalcare.carehalcare_manage.Feature_carereport.Clean;

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

import java.util.ArrayList;
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

    Long ids;  //TODO ids는 삭제할 id값
    private ArrayList<Clean_ResponseDTO> cleanArrayList;
    private Clean_adapter cleanAdapter;
    private ArrayList<Clean_texthist> histArrayList;
    private Clean_adapterhist histAdapter;


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


        // Clean_API cleanApi = retrofit.create(Clean_API.class);
        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        cleanArrayList = new ArrayList<>();
        cleanAdapter = new Clean_adapter( cleanArrayList);
        mRecyclerView.setAdapter(cleanAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        cleanApi.getDataClean(userid,puserid).enqueue(new Callback<List<Clean_ResponseDTO>>() {
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
                                Log.e("현재id : " + i, datas.get(i).getCleanliness() + " " + datas.get(i).getId() + "" + "어댑터카운터" + cleanAdapter.getItemCount());

                            }
                            Log.e("getSleep success", "======================================");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Clean_ResponseDTO>> call, Throwable t) {
                Log.e("getSleep fail", "======================================");
            }
        });


        cleanAdapter.setOnItemClickListener (new Clean_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Clean_ResponseDTO detail_clean_text = cleanArrayList.get(position);
                Long clicked = detail_clean_text.getId();

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

                // Create and set Clean_adapterhist adapter for the histRecyclerView
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
                                if (datas != null) {
                                    histArrayList.clear();
                                    histArrayList.addAll(datas); // Populate the correct list

                                    histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                    Log.e("get Hist success", datas+ "======================================");

                                    histAdapter.setOnItemClickListener(new Clean_adapterhist.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int pos) {
                                            Clean_texthist hist = histArrayList.get(pos);

                                            AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                            View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.clean_detail, null, false);
                                            histBuilder.setView(detailDialog);

                                            final AlertDialog hdialog = histBuilder.create();
                                            hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            hdialog.show();

                                            final TextView detail_sheet = hdialog.findViewById(R.id.tv_cleandatail_sheet);
                                            final TextView detail_cloth = hdialog.findViewById(R.id.tv_cleandatail_cloth);
                                            final TextView detail_ventilation = hdialog.findViewById(R.id.tv_cleandatail_ventilation);
                                            final TextView et_detail_clean = hdialog.findViewById(R.id.tv_cleandetail_et);

                                            String sheet_cloth_ventilation = hist.getCleanliness();
                                            String content_detail = hist.getContent();

                                            if (sheet_cloth_ventilation.contains("시트변경완료")) detail_sheet.setText("시트변경완료");
                                            else detail_sheet.setText("해당사항 없음");

                                            if (sheet_cloth_ventilation.contains("환의교체완료")) detail_cloth.setText("환의교체완료");
                                            else detail_cloth.setText("해당사항 없음");

                                            if (sheet_cloth_ventilation.contains("환기완료")) detail_ventilation.setText("환기완료");
                                            else detail_ventilation.setText("해당사항 없음");

                                            if (content_detail.equals("-")) et_detail_clean.setText("없음");
                                            else et_detail_clean.setText(content_detail);

                                            Button btn_cleandetail = hdialog.findViewById(R.id.btn_cleandtail);

                                            btn_cleandetail.setOnClickListener(new View.OnClickListener() {
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
                                    Log.e("not loaded", datas.size() + "======================================");
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
        return view;
    }
}