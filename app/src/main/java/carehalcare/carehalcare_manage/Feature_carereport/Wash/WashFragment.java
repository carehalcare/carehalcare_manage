package carehalcare.carehalcare_manage.Feature_carereport.Wash;

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

public class WashFragment extends Fragment {
    String userid,puserid;
    Long ids;
    private ArrayList<Wash_text_modified> washArrayList;
    private Wash_adapter washAdapter;
    private ArrayList<Wash_texthist> histArrayList;
    private Wash_adapterhist histAdapter;
    private Button btn_update, btn_off;


    public WashFragment() {
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

        Wash_API washApi = Retrofit_client.createService(Wash_API.class, TokenUtils.getAccessToken("Access_Token"));

        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        washArrayList = new ArrayList<>();
        washAdapter = new Wash_adapter( washArrayList);
        mRecyclerView.setAdapter(washAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        washApi.getDataWash(userid,puserid).enqueue(new Callback<List<Wash_ResponseDTO>>() {
            @Override
            public void onResponse(Call<List<Wash_ResponseDTO>> call, Response<List<Wash_ResponseDTO>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        List<Wash_ResponseDTO> datas = response.body();
                        if (datas != null) {
                            washArrayList.clear();
                            List<Wash_text_modified> tempList = new ArrayList<>();

                            for (int i = 0; i < datas.size(); i++) {
                                Long activeId = datas.get(i).getId();
                                int finalI = i;

                                washApi.gethistPclean(activeId).enqueue(new Callback<List<Wash_texthist>>() {
                                    @Override
                                    public void onResponse(Call<List<Wash_texthist>> call, Response<List<Wash_texthist>> response) {
                                        if (response.isSuccessful()) {
                                            List<Wash_texthist> histDatas = response.body();
                                            boolean isModified = (histDatas != null && histDatas.size() > 1);

                                            Wash_text_modified modifiedItem = new Wash_text_modified(
                                                    datas.get(finalI).getId(),
                                                    datas.get(finalI).getUserId(),
                                                    datas.get(finalI).getPuserId(),
                                                    datas.get(finalI).getCleanliness(),
                                                    datas.get(finalI).getPart(),
                                                    datas.get(finalI).getContent(),
                                                    datas.get(finalI).getCreatedDateTime(),
                                                    isModified
                                            );

                                            tempList.add(modifiedItem);

                                            if (tempList.size() == datas.size()) {
                                                Collections.sort(tempList, new Comparator<Wash_text_modified>() {
                                                    @Override
                                                    public int compare(Wash_text_modified item1, Wash_text_modified item2) {
                                                        return item2.getCreatedDateTime().compareTo(item1.getCreatedDateTime());
                                                    }
                                                });

                                                washArrayList.clear();
                                                washArrayList.addAll(tempList);

                                                washAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Wash_texthist>> call, Throwable t) {
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
            public void onFailure(Call<List<Wash_ResponseDTO>> call, Throwable t) {
                // 실패 시의 처리 로직
            }
        });


        washAdapter.setOnItemClickListener (new Wash_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Wash_ResponseDTO detail_wash_text = washArrayList.get(position);
                Long clicked = detail_wash_text.getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.wash_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                final TextView tv_face = dialog.findViewById(R.id.tv_washdetail_face);
                final TextView tv_mouth = dialog.findViewById(R.id.tv_washdetail_mouth);
                final TextView tv_nail = dialog.findViewById(R.id.tv_washdetail_nail);
                final TextView tv_hair = dialog.findViewById(R.id.tv_washdetail_hair);
                final TextView tv_scrub = dialog.findViewById(R.id.tv_washdetail_scrub);
                final TextView tv_shave = dialog.findViewById(R.id.tv_washdetail_shave);
                final TextView tv_scrub_point = dialog.findViewById(R.id.tv_washdetail_scrub_point);
                final TextView tv_et = dialog.findViewById(R.id.tv_washdetail_et);

                String cleantype[] = detail_wash_text.getCleanliness().split(" ");
                String part = detail_wash_text.getPart();
                String content_detail = detail_wash_text.getContent();

                tv_scrub_point.setText(part);

                if (content_detail.equals("-"))
                    tv_et.setText("-");
                else tv_et.setText(content_detail);

                if (cleantype[0].equals("Y")) {
                    tv_face.setText("완료");
                } else tv_face.setText("-");
                if (cleantype[1].equals("Y")) {
                    tv_mouth.setText("완료");
                } else tv_mouth.setText("-");
                if (cleantype[2].equals("Y"))
                    tv_nail.setText("완료");
                else tv_nail.setText("-");
                if (cleantype[3].equals("Y"))
                    tv_hair.setText("완료");
                else tv_hair.setText("-");
                if (cleantype[4].equals("Y"))
                    tv_scrub.setText("완료");
                else tv_scrub.setText("-");
                if (cleantype[5].equals("Y")) {
                    tv_shave.setText("완료");
                } else tv_shave.setText("-");


                btn_update = dialog.findViewById(R.id.btn_update_report);
                btn_off = dialog.findViewById(R.id.btn_wash_detail);

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

                        // Create and set Wash_adapterhist adapter for the histRecyclerView
                        histArrayList = new ArrayList<>();
                        histAdapter = new Wash_adapterhist(histArrayList);
                        histRecyclerView.setAdapter(histAdapter);

                        Button btn_out = dialog.findViewById(R.id.btn_out);

                        btn_out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        washApi.gethistPclean(clicked).enqueue(new Callback<List<Wash_texthist>>() {
                            @Override
                            public void onResponse(Call<List<Wash_texthist>> call, Response<List<Wash_texthist>> response) {

                                Log.e("not loaded", response.body() + "======================================");
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        List<Wash_texthist> datas = response.body();
                                        if (datas != null && datas.size() > 1) {
                                            histArrayList.clear();
                                            histArrayList.addAll(datas); // Populate the correct list

                                            histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                            Log.e("get Hist success", datas + "======================================");

                                            histAdapter.setOnItemClickListener(new Wash_adapterhist.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View v, int pos) {
                                                    Wash_texthist hist = histArrayList.get(pos);

                                                    AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                                    View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.wash_detail_hist, null, false);
                                                    histBuilder.setView(detailDialog);

                                                    final AlertDialog hdialog = histBuilder.create();
                                                    hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    hdialog.show();

                                                    final TextView tv_face = hdialog.findViewById(R.id.tv_washdetail_face);
                                                    final TextView tv_mouth = hdialog.findViewById(R.id.tv_washdetail_mouth);
                                                    final TextView tv_nail = hdialog.findViewById(R.id.tv_washdetail_nail);
                                                    final TextView tv_hair = hdialog.findViewById(R.id.tv_washdetail_hair);
                                                    final TextView tv_scrub = hdialog.findViewById(R.id.tv_washdetail_scrub);
                                                    final TextView tv_shave = hdialog.findViewById(R.id.tv_washdetail_shave);
                                                    final TextView tv_scrub_point = hdialog.findViewById(R.id.tv_washdetail_scrub_point);
                                                    final TextView tv_et = hdialog.findViewById(R.id.tv_washdetail_et);

                                                    String cleantype[] = hist.getCleanliness().split(" ");
                                                    String part = hist.getPart();
                                                    String content_detail = hist.getContent();

                                                    tv_scrub_point.setText(part);

                                                    if (content_detail.equals("-"))
                                                        tv_et.setText("-");
                                                    else tv_et.setText(content_detail);

                                                    if (cleantype[0].equals("Y")) {
                                                        tv_face.setText("완료");
                                                    } else tv_face.setText("-");
                                                    if (cleantype[1].equals("Y")) {
                                                        tv_mouth.setText("완료");
                                                    } else tv_mouth.setText("-");
                                                    if (cleantype[2].equals("Y"))
                                                        tv_nail.setText("완료");
                                                    else tv_nail.setText("-");
                                                    if (cleantype[3].equals("Y"))
                                                        tv_hair.setText("완료");
                                                    else tv_hair.setText("-");
                                                    if (cleantype[4].equals("Y"))
                                                        tv_scrub.setText("완료");
                                                    else tv_scrub.setText("-");
                                                    if (cleantype[5].equals("Y")) {
                                                        tv_shave.setText("완료");
                                                    } else tv_shave.setText("-");

                                                    final Button btn_washclose = hdialog.findViewById(R.id.btn_wash_detail);

                                                    btn_washclose.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            hdialog.dismiss();
                                                        }
                                                    });

                                                    washApi.gethistPclean_detail(clicked, pos).enqueue(new Callback<List<Wash_texthist>>() {
                                                        @Override
                                                        public void onResponse(Call<List<Wash_texthist>> call, Response<List<Wash_texthist>> response) {
                                                            if (response.isSuccessful()) {
                                                                List<Wash_texthist> detail = response.body();
                                                                if (detail != null) {

                                                                    Log.e("Detail OK", detail + "------------");
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<Wash_texthist>> call, Throwable t) {
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
                            public void onFailure(Call<List<Wash_texthist>> call, Throwable t) {
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