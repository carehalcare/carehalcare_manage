package carehalcare.carehalcare_manage.Feature_carereport.Wash;

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

import java.util.ArrayList;

import carehalcare.carehalcare_manage.Feature_carereport.DateUtils;
import carehalcare.carehalcare_manage.R;

public class Wash_adapter extends RecyclerView.Adapter<Wash_adapter.CustomViewHolder>{
    private ArrayList<Wash_text_modified> mList;
    private Context mContext;

    public Wash_adapter(ArrayList<Wash_text_modified> list) {
        this.mList = list;
    }

    //아이템 클릭 리스너 인터페이스
    public interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }

    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        protected TextView tv_Washdate, tv_Result, tv_update;

        public CustomViewHolder(View view) {
            super(view);
            this.tv_Result = (TextView) view.findViewById(R.id.tv_todayWash);
            this.tv_Washdate = (TextView) view.findViewById(R.id.tv_todayWashResult);
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

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.wash_onelist, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.tv_Washdate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewholder.tv_Result.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        viewholder.tv_Washdate.setGravity(Gravity.CENTER);
        viewholder.tv_Result.setGravity(Gravity.CENTER);

        // Content 설정
        String date = mList.get(position).getCreatedDateTime();
        String formattedDate = DateUtils.formatDatestring(date);

        String cleanliness = mList.get(position).getCleanliness();
        String part = mList.get(position).getPart();
        String content = mList.get(position).getContent();

        viewholder.tv_Washdate.setText(formattedDate);
        viewholder.tv_Result.setText("청결 기록 확인하기");

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
