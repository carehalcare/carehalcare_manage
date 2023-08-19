package carehalcare.carehalcare_manage.Feature_carereport.Active;

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
import androidx.collection.ArraySet;
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


public class ActiveFragment extends Fragment {
    String userid,puserid;
    Long ids;  //TODO ids는 삭제할 id값
    private ArrayList<Active_text_modified> activeArrayList;
    private Active_adapter activeAdapter;
    private ArrayList<Active_texthist> histArrayList;
    private Active_adapterhist histAdapter;

    private TextView activedetail_jahal, activedetail_bohang, activedetail_change;
    private Button btn_off, btn_update;

    public ActiveFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, container, false);

        userid = this.getArguments().getString("userid");
        puserid = this.getArguments().getString("puserid");

        Active_API activeApi = Retrofit_client.createService(Active_API.class, TokenUtils.getAccessToken("Access_Token"));

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        activeArrayList = new ArrayList<>();

        activeAdapter = new Active_adapter(activeArrayList);
        mRecyclerView.setAdapter(activeAdapter);

        activeApi.getDataActive(userid, puserid).enqueue(new Callback<List<Active_text>>() {
            @Override
            public void onResponse(Call<List<Active_text>> call, Response<List<Active_text>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Active_text> datas = response.body();
                        if (datas != null) {
                            activeArrayList.clear();
                            List<Active_text_modified> tempList = new ArrayList<>();

                            for (int i = 0; i < datas.size(); i++) {
                                Long activeId = datas.get(i).getId();
                                int finalI = i;

                                activeApi.gethistActive(activeId).enqueue(new Callback<List<Active_texthist>>() {
                                    @Override
                                    public void onResponse(Call<List<Active_texthist>> call, Response<List<Active_texthist>> response) {
                                        if (response.isSuccessful()) {
                                            List<Active_texthist> histDatas = response.body();
                                            boolean isModified = (histDatas != null && histDatas.size() > 1);

                                            Active_text_modified modifiedItem = new Active_text_modified(
                                                    datas.get(finalI).getId(),
                                                    datas.get(finalI).getUserId(),
                                                    datas.get(finalI).getPuserId(),
                                                    datas.get(finalI).getRehabilitation(),
                                                    datas.get(finalI).getWalkingAssistance(),
                                                    datas.get(finalI).getPosition(),
                                                    datas.get(finalI).getCreatedDateTime(),
                                                    isModified
                                            );

                                            tempList.add(modifiedItem);

                                            if (tempList.size() == datas.size()) {
                                                Collections.sort(tempList, new Comparator<Active_text_modified>() {
                                                    @Override
                                                    public int compare(Active_text_modified item1, Active_text_modified item2) {
                                                        return item2.getCreatedDateTime().compareTo(item1.getCreatedDateTime());
                                                    }
                                                });

                                                activeArrayList.clear();
                                                activeArrayList.addAll(tempList);

                                                activeAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Active_texthist>> call, Throwable t) {
                                        // 처리 실패 시의 로직
                                    }
                                });
                            }

                            Log.e("getActive success", "======================================");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Active_text>> call, Throwable t) {
                // 실패 시의 처리 로직
            }
        });

        activeAdapter.setOnItemClickListener (new Active_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Active_text detail_active_text = activeArrayList.get(position);
                Long clicked = detail_active_text.getId();
                activeApi.getDataActive(userid, puserid).enqueue(new Callback<List<Active_text>>() {
                    @Override
                    public void onResponse(Call<List<Active_text>> call, Response<List<Active_text>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Active_text> datas = response.body();
                                if (datas != null) {
                                    ids = response.body().get(position).getId();
                                    Log.e("지금 position : ", position + "이고 DB ID는 : " + ids);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Active_text>> call, Throwable t) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.active_detail, null, false);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

                activedetail_jahal = dialog.findViewById(R.id.tv_activedetail_jahal);
                activedetail_bohang = dialog.findViewById(R.id.tv_activedetail_bohang);
                activedetail_change = dialog.findViewById(R.id.tv_activedetail_change);

                String getjahal = detail_active_text.getRehabilitation();
                String getbohang = detail_active_text.getWalkingAssistance();
                String getchange = detail_active_text.getPosition();

                if (getjahal.equals("Y"))
                    activedetail_jahal.setText("완료");
                else activedetail_jahal.setText("-");

                if (getbohang.equals("Y"))
                    activedetail_bohang.setText("완료");
                else activedetail_bohang.setText("-");

                if (getchange.equals("Y"))
                    activedetail_change.setText("완료");
                else activedetail_change.setText("-");

                btn_off = dialog.findViewById(R.id.btn_active_detail);
                btn_update = dialog.findViewById(R.id.btn_update_report);

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

                        // Create and set Active_adapterhist adapter for the histRecyclerView
                        histArrayList = new ArrayList<>();
                        histAdapter = new Active_adapterhist(histArrayList);
                        histRecyclerView.setAdapter(histAdapter);

                        Button btn_out = dialog.findViewById(R.id.btn_out);

                        btn_out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        activeApi.gethistActive(clicked).enqueue(new Callback<List<Active_texthist>>() {
                            @Override
                            public void onResponse(Call<List<Active_texthist>> call, Response<List<Active_texthist>> response) {
                                if (response.isSuccessful()) {
                                    List<Active_texthist> datas = response.body();
                                    if (datas != null && datas.size() > 1) {
                                        histArrayList.clear();
                                        histArrayList.addAll(datas);

                                        histAdapter.notifyDataSetChanged();

                                        Log.e("get Hist success", datas +"======================================");

                                        histAdapter.setOnItemClickListener(new Active_adapterhist.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View v, int pos) {
                                                Active_texthist hist = histArrayList.get(pos);

                                                AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                                View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.active_detail_hist, null, false);
                                                histBuilder.setView(detailDialog);

                                                final AlertDialog hdialog = histBuilder.create();
                                                hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                hdialog.show();

                                                activeApi.gethistActive_detail(clicked, pos).enqueue(new Callback<List<Active_texthist>>() {
                                                    @Override
                                                    public void onResponse(Call<List<Active_texthist>> call, Response<List<Active_texthist>> response) {
                                                        if (response.isSuccessful()) {
                                                            List<Active_texthist> detail = response.body();
                                                            if (detail != null) {

                                                                Log.e("Detail OK",detail + "------------");
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<Active_texthist>> call, Throwable t) {
                                                        Log.e("Detail Fetch Failure", t.getMessage());
                                                    }
                                                });

                                                activedetail_jahal = hdialog.findViewById(R.id.tv_activedetail_jahal);
                                                activedetail_bohang = hdialog.findViewById(R.id.tv_activedetail_bohang);
                                                activedetail_change = hdialog.findViewById(R.id.tv_activedetail_change);

                                                String getjahal = hist.getRehabilitation();
                                                String getbohang = hist.getWalkingAssistance();
                                                String getchange = hist.getPosition();

                                                if(getjahal.equals("Y"))
                                                    activedetail_jahal.setText("완료");
                                                else activedetail_jahal.setText("-");

                                                if(getbohang.equals("Y"))
                                                    activedetail_bohang.setText("완료");
                                                else activedetail_bohang.setText("-");

                                                if (getchange.equals("Y"))
                                                    activedetail_change.setText("완료");
                                                else activedetail_change.setText("-");

                                                Button btn_off = hdialog.findViewById(R.id.btn_active_detail);

                                                btn_off.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        hdialog.dismiss();
                                                    }
                                                });

                                            }
                                        });

                                    }
                                    else {

                                        AlertDialog.Builder noHistBuilder = new AlertDialog.Builder(getContext());
                                        noHistBuilder.setMessage("수정된 기록이 없습니다.")
                                                .setPositiveButton("확인", null)
                                                .create()
                                                .show();

                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Active_texthist>> call, Throwable t) {
                                Log.e("Histlist Failure",  t.getMessage()+ "======================================");
                            }
                        });

                    }
                });
            }
        });

        return view;
    }

}