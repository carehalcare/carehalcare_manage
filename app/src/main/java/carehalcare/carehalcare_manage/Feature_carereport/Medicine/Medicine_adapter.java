package carehalcare.carehalcare_manage.Feature_carereport.Medicine;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.DateUtils;
import carehalcare.carehalcare_manage.R;

public class Medicine_adapter extends RecyclerView.Adapter<Medicine_adapter.CustomViewHolder>{
    private ArrayList<Medicine_text> mList;
    private List<Medicine_text> gList;
    private Context mContext;

    public Medicine_adapter (ArrayList<Medicine_text> list) { this.mList = list;}
    public Medicine_adapter(Context mContext, List<Medicine_text> list) {
        this.mContext = mContext;
        this.gList = list;
    }

    // 클릭 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //리스너 객체 참조 변수
    private Medicine_adapter.OnItemClickListener mListener = null;

    // 외부에서 클릭 리스너 설정하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Medicine_adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_onelist, parent, false);
        Medicine_adapter.CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Medicine_adapter.CustomViewHolder holder, int position) {

        holder.tv_medidate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        holder.tv_result.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        holder.tv_medidate.setGravity(Gravity.CENTER);
        holder.tv_result.setGravity(Gravity.CENTER);

        // Content 설정
        String date = mList.get(position).getCreatedDateTime();
        String formattedDate = DateUtils.formatDate(date);

        holder.tv_medidate.setText(formattedDate);
        holder.tv_result.setText("약 복용 기록 확인하기");
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tv_medidate, tv_result;

        public CustomViewHolder(View view) {
            super(view);
            tv_medidate = (TextView) view.findViewById(R.id.tv_todayMedicine);
            tv_result = (TextView) view.findViewById(R.id.tv_todayMedicineResult);

            view.setOnCreateContextMenuListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onItemClick(view, position); // 클릭 리스너 콜백 호출
                    }
                }
            });
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다.
        }


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
