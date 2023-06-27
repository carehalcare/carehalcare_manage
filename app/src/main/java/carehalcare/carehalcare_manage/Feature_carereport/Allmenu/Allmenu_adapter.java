package carehalcare.carehalcare_manage.Feature_carereport.Allmenu;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import carehalcare.carehalcare_manage.R;
import carehalcare.carehalcare_manage.R;

public class Allmenu_adapter extends RecyclerView.Adapter<Allmenu_adapter.CustomViewHolder> {
    private ArrayList<BoardResponseDto> mList;
    private Context mContext;
    String menu;

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
        protected TextView tv_allmenu_Category;
        protected TextView tv_allmenu_Date;
        protected ImageView iv_allmenu_;


        public CustomViewHolder(View view) {
            super(view);
            this.tv_allmenu_Category = (TextView) view.findViewById(R.id.tv_allmenu_category);
            this.tv_allmenu_Date = (TextView) view.findViewById(R.id.tv_allmenu_date);
            this.iv_allmenu_ = (ImageView) view.findViewById(R.id.iv_allmenu);

            view.setOnCreateContextMenuListener(this);

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
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다. ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.

            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);


        }
        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {



            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        };

    }


    public Allmenu_adapter(ArrayList<BoardResponseDto> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.allmenu_onelist, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.tv_allmenu_Category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        viewholder.tv_allmenu_Date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        //viewholder.tv_allmenu_Category.setGravity(Gravity.CENTER);
        //viewholder.tv_allmenu_Date.setGravity(Gravity.CENTER);
        String category = "";

        switch (mList.get(position).getCategory()){
            case "activities":
                category = "활동";
                viewholder.iv_allmenu_.setImageResource(R.drawable.activity);
                break;
            case "administrations":
                category = "약 복용";
                viewholder.iv_allmenu_.setImageResource(R.drawable.medicine);
                break;
            case "bowelmovements":
                category = "배변";
                viewholder.iv_allmenu_.setImageResource(R.drawable.toilet);

                break;
            case "meals":
                category = "식사";
                viewholder.iv_allmenu_.setImageResource(R.drawable.meal);
                break;
            case "pcleanliness":
                category = "환자청결";
                viewholder.iv_allmenu_.setImageResource(R.drawable.wash);
                break;
            case "sleepstates":
                category = "수면상태";
                viewholder.iv_allmenu_.setImageResource(R.drawable.sleep);
                break;
            case "scleanliness":
                category = "주변환경 청결";
                viewholder.iv_allmenu_.setImageResource(R.drawable.clean);
                break;
            case "walks":
                category = "산책";
                viewholder.iv_allmenu_.setImageResource(R.drawable.walk);
                break;
            default: category ="default"; break;


        }

        viewholder.tv_allmenu_Category.setText(category+" 기록 확인하기");
        Date today_date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 M월 dd일", Locale.getDefault());
        String activeTodayResult = format.format(today_date)+" 기록";
        viewholder.tv_allmenu_Date.setText(formatDate(mList.get(position).getCreatedDateTime()));

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
    private String formatDate(String dateStr) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss", Locale.KOREA);
        String newDate = "";
        try {
            Date date = originalFormat.parse(dateStr);
            newDate = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

}
