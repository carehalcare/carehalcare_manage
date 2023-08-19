package carehalcare.carehalcare_manage.Feature_carereport.Sleep;

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

import carehalcare.carehalcare_manage.Feature_carereport.DateUtils;
import carehalcare.carehalcare_manage.R;

public class Sleep_adapter extends RecyclerView.Adapter<Sleep_adapter.CustomViewHolder>{
    private ArrayList<Sleep_text_modified> mList;
    private Context mContext;

    //아이템 클릭 리스너 인터페이스
    public interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }
    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        protected TextView tv_todaySleep, tv_todaySleepResult, tv_update;

        public CustomViewHolder(View view) {
            super(view);
            this.tv_todaySleepResult = (TextView) view.findViewById(R.id.tv_todaySleep);
            this.tv_todaySleep = (TextView) view.findViewById(R.id.tv_todaySleepResult);
            this.tv_update = (TextView) view.findViewById(R.id.tv_update);

            view.setOnCreateContextMenuListener(this);
            //2. OnCreateContextMenuListener 리스너를 현재 클래스에서 구현한다고 설정해둡니다.

            view.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition ();
                    if (position!=RecyclerView.NO_POSITION){
                        if (mListener!=null){
                            mListener.onItemClick (view,position);
                        }
                    }
                }
            });
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

    public Sleep_adapter(ArrayList<Sleep_text_modified> list) {
        this.mList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sleep_onelist, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.tv_todaySleep.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewholder.tv_todaySleepResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        viewholder.tv_todaySleep.setGravity(Gravity.CENTER);
        viewholder.tv_todaySleepResult.setGravity(Gravity.CENTER);

        // Content 설정
        String date = mList.get(position).getCreatedDateTime();
        String formattedDate = DateUtils.formatDatestring(date);

        viewholder.tv_todaySleep.setText(formattedDate);
        viewholder.tv_todaySleepResult.setText( "수면 기록 확인하기");

        if (mList.get(position).isModified() == true) {
            viewholder.tv_update.setText("수정됨");
            viewholder.tv_update.setVisibility(View.VISIBLE);
        } else {
            viewholder.tv_update.setText("");
        }


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
