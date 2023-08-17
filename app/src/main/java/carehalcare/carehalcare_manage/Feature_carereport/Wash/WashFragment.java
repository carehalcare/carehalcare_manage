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

public class WashFragment extends Fragment {
    String userid,puserid;
    private ArrayList<Wash_ResponseDTO> washArrayList;
    private Wash_adapter washAdapter;
    private ArrayList<Wash_texthist> histArrayList;
    private Wash_adapterhist histAdapter;


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
                }
            }
            @Override
            public void onFailure(Call<List<Wash_ResponseDTO>> call, Throwable t) {
            }
        });


        washAdapter.setOnItemClickListener (new Wash_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Wash_ResponseDTO detail_wash_text = washArrayList.get(position);
                Long clicked = detail_wash_text.getId();

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
                                if (datas != null) {
                                    histArrayList.clear();
                                    histArrayList.addAll(datas); // Populate the correct list

                                    histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                    Log.e("get Hist success", datas+ "======================================");

                                    histAdapter.setOnItemClickListener(new Wash_adapterhist.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int pos) {
                                            Wash_texthist hist = histArrayList.get(pos);

                                            AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                            View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.wash_detail, null, false);
                                            histBuilder.setView(detailDialog);

                                            final AlertDialog hdialog = histBuilder.create();
                                            hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            hdialog.show();

                                            final TextView tv_face = hdialog.findViewById(R.id.tv_washdetail_face);
                                            final TextView tv_mouth = hdialog.findViewById(R.id.tv_washdetail_mouth);
                                            final TextView tv_nail = hdialog.findViewById(R.id.tv_washdetail_nail);
                                            final TextView tv_hair  = hdialog.findViewById(R.id.tv_washdetail_hair);
                                            final TextView tv_scrub  = hdialog.findViewById(R.id.tv_washdetail_scrub);
                                            final TextView tv_shave  = hdialog.findViewById(R.id.tv_washdetail_shave);
                                            final TextView tv_scrub_point  = hdialog.findViewById(R.id.tv_washdetail_scrub_point);
                                            final TextView tv_et  = hdialog.findViewById(R.id.tv_washdetail_et);

                                            String cleantype = hist.getCleanliness();
                                            String part = hist.getPart();
                                            String content_detail = hist.getContent();

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
                                    Log.e("not loaded", datas.size() + "======================================");
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
        return view;
    }
}