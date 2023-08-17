package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import static carehalcare.carehalcare_manage.Feature_carereport.DateUtils.formatDatestring;

import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import carehalcare.carehalcare_manage.R;

public class Meal_adapterhist extends RecyclerView.Adapter<Meal_adapterhist.CustomViewHolder>{

    private ArrayList<Meal_texthist> mList;

    public interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }

    //리스너 객체 참조 변수
    private Meal_adapterhist.OnItemClickListener mListener = null;

    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(Meal_adapterhist.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        protected TextView tv_content;


        public CustomViewHolder(View view) {
            super(view);
            this.tv_content = (TextView) view.findViewById(R.id.tv_content);

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

    public Meal_adapterhist(ArrayList<Meal_texthist> list) {
        this.mList = list;
    }

    @Override
    public Meal_adapterhist.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.report_changelist, viewGroup, false);

        Meal_adapterhist.CustomViewHolder viewHolder = new Meal_adapterhist.CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull Meal_adapterhist.CustomViewHolder viewholder, int position) {

        viewholder.tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewholder.tv_content.setGravity(Gravity.CENTER);

        String date = mList.get(position).getModifiedDateTime();
        String dateformat = formatDatestring(date);


        viewholder.tv_content. setText(dateformat + "의 기록 확인하기");
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
