package carehalcare.carehalcare_manage.Feature_carereport.Meal;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import carehalcare.carehalcare_manage.Feature_carereport.DateUtils;
import carehalcare.carehalcare_manage.R;

public class Meal_adapter extends RecyclerView.Adapter<Meal_adapter.CustomViewHolder>{
    private ArrayList<Meal_ResponseDTO> mList;
    private Context mContext;

    //아이템 클릭 리스너 인터페이스
    public interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }
    //리스너 객체 참조 변수
    public OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        protected ImageView iv_mealpic;
        protected TextView tv_mealcontent, tv_date;

        public CustomViewHolder(View view) {
            super(view);

            this.tv_date = (TextView) view.findViewById(R.id.iv_meal_title);
            this.iv_mealpic = (ImageView) view.findViewById(R.id.iv_mealpic);
            this.tv_mealcontent = (TextView) view.findViewById(R.id.tv_mealcontent);

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
            // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다. ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.

            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);


        }
        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        public final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
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
    public Meal_adapter(ArrayList<Meal_ResponseDTO> list) {
        this.mList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.meal_onelist, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.tv_mealcontent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        viewholder.tv_mealcontent.setGravity(Gravity.CENTER);


        List<Meal_Image> images = mList.get(position).getImages();

        String date = mList.get(position).getCreatedDateTime();
        String formattedDate = DateUtils.formatDate(date);
        viewholder.tv_date.setText(formattedDate);
        viewholder.tv_mealcontent.setText(mList.get(position).getContent());

        if (images != null && !images.isEmpty()) {
            String filePath = images.get(0).getFilePath();
            Glide.with(viewholder.itemView.getContext()).load(filePath).into(viewholder.iv_mealpic);
        } else {
            // 이미지가 없는 경우 기본 이미지 설정 또는 처리 로직 추가
        }
    }


    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}