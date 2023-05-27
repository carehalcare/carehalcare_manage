package carehalcare.carehalcare_manage.Feature_carereport.Sleep;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import carehalcare.carehalcare_manage.R;

public class Sleep_adapter extends RecyclerView.Adapter<Sleep_adapter.CustomViewHolder>{
    private ArrayList<Sleep_text> mList;
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
        protected TextView tv_todaySleep;
        protected TextView tv_todaySleepResult;



        public CustomViewHolder(View view) {
            super(view);
            this.tv_todaySleep = (TextView) view.findViewById(R.id.tv_todaySleep);
            this.tv_todaySleepResult = (TextView) view.findViewById(R.id.tv_todaySleepResult);

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
            // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다.
            // ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1002:
                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());

                        break;
                }
                return true;
            }
        };

    }

    public Sleep_adapter(ArrayList<Sleep_text> list) {
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

        viewholder.tv_todaySleep.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        viewholder.tv_todaySleepResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewholder.tv_todaySleep.setGravity(Gravity.CENTER);
        viewholder.tv_todaySleepResult.setGravity(Gravity.CENTER);

        viewholder.tv_todaySleep.setText("수면상태");
        //String seeText = mList.get(position).getSleepTodayResult();
        //if (seeText.length() >= 25){seeText = seeText.substring(0,25);};
        //viewholder.tv_todayCleanResult.setText(seeText+" ···");
        Date today_date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 M월 dd일", Locale.getDefault());
        String sleepTodayResult = format.format(today_date)+" 기록확인하기";
        viewholder.tv_todaySleepResult.setText(sleepTodayResult);


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
