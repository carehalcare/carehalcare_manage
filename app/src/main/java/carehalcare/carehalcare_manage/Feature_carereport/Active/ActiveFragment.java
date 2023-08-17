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


public class ActiveFragment extends Fragment {
    String userid,puserid;
    Long ids;  //TODO ids는 삭제할 id값
    private ArrayList<Active_text> activeArrayList;
    private Active_adapter activeAdapter;
    private ArrayList<Active_texthist> histArrayList;

    private Active_adapterhist histAdapter;

    private TextView activedetail_jahal, activedetail_bohang, activedetail_change;

    public ActiveFragment() {

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

        Active_API activeApi = Retrofit_client.createService(Active_API.class, TokenUtils.getAccessToken("Access_Token"));

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        activeArrayList = new ArrayList<>();

        activeAdapter = new Active_adapter( activeArrayList);
        mRecyclerView.setAdapter(activeAdapter);

        activeApi.getDataActive(userid,puserid).enqueue(new Callback<List<Active_text>>() {
            @Override
            public void onResponse(Call<List<Active_text>> call, Response<List<Active_text>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Active_text> datas = response.body();
                        if (datas != null) {
                            activeArrayList.clear();
                            for (int i = 0; i < datas.size(); i++) {
                                Active_text dict_0 = new Active_text(response.body().get(i).getCreatedDateTime(),
                                        response.body().get(i).getId(), response.body().get(i).getPosition(),
                                        response.body().get(i).getPuserId(), response.body().get(i).getRehabilitation(),
                                        response.body().get(i).getUserId(), response.body().get(i).getWalkingAssistance());
                                activeArrayList.add(dict_0);
                                activeAdapter.notifyItemInserted(0);
                                Log.e("현재id : " + i, datas.get(i).getPosition()+" "+datas.get(i).getId() + ""+"어댑터카운터"+activeAdapter.getItemCount());
                            }
                            Log.e("getActive success", "======================================");
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Active_text>> call, Throwable t) {
            }
        });

        activeAdapter.setOnItemClickListener (new Active_adapter.OnItemClickListener () {
            @Override
            public void onItemClick(View v, int position) {
                Active_text detail_active_text = activeArrayList.get(position);
                Long clicked = detail_active_text.getId();

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
                            if (response.body() != null) {
                                List<Active_texthist> datas = response.body();
                                if (datas != null) {
                                    histArrayList.clear();
                                    histArrayList.addAll(datas); // Populate the correct list

                                    histAdapter.notifyDataSetChanged(); // Notify adapter of data change

                                    Log.e("get Hist success", datas +"======================================");

                                    histAdapter.setOnItemClickListener(new Active_adapterhist.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int pos) {
                                            Active_texthist hist = histArrayList.get(pos);

                                            AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
                                            View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.active_detail, null, false);
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
                                                activedetail_jahal.setText("재활치료 완료");
                                            else activedetail_jahal.setText("X");

                                            if(getbohang.equals("Y"))
                                                activedetail_bohang.setText("보행도움 완료");
                                            else activedetail_bohang.setText("X");

                                            if (getchange.equals("Y"))
                                                activedetail_change.setText("체위변경 완료");
                                            else activedetail_change.setText("X");

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
                                else{
                                    Log.e("not loaded", datas.size()+ "======================================");
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Active_texthist>> call, Throwable t) {
                        Log.e("Histlist Failure",  t.getMessage()+ "======================================");
                    }
                });


//                histAdapter.setOnItemClickListener(new Active_adapterhist.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View v, int pos) {
//                        // Get the clicked item from histArrayList
//                        Active_texthist hist = histArrayList.get(pos);;
//
//                        AlertDialog.Builder histBuilder = new AlertDialog.Builder(getContext());
//                        View detailDialog = LayoutInflater.from(getContext()).inflate(R.layout.active_detail, null, false);
//
//                        histBuilder.setView(detailDialog);
//
//                        final AlertDialog hdialog = histBuilder.create();
//                        hdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                        hdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        hdialog.show();
//
//                        activedetail_jahal = hdialog.findViewById(R.id.tv_activedetail_jahal);
//                        activedetail_bohang = hdialog.findViewById(R.id.tv_activedetail_bohang);
//                        activedetail_change = hdialog.findViewById(R.id.tv_activedetail_change);
//
//                        String getjahal = hist.getRehabilitation();
//                        String getbohang = hist.getWalkingAssistance();
//                        String getchange = hist.getPosition();
//
//                        if(getjahal.equals("Y"))
//                            activedetail_jahal.setText("재활치료 완료");
//                        else activedetail_jahal.setText("X");
//
//                        if(getbohang.equals("Y"))
//                            activedetail_bohang.setText("보행도움 완료");
//                        else activedetail_bohang.setText("X");
//
//                        if (getchange.equals("Y"))
//                            activedetail_change.setText("체위변경 완료");
//                        else activedetail_change.setText("X");
//
//                        Button btn_off = hdialog.findViewById(R.id.btn_active_detail);
//
//                        btn_off.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                hdialog.dismiss();
//                            }
//                        });
//                    }
//                });
//
            }
        });
        return view;
    }

}