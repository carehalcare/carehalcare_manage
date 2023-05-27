package carehalcare.carehalcare_manage.Feature_carereport.Medicine;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import carehalcare.carehalcare_manage.R;

public class Medicine_adapter extends RecyclerView.Adapter<Medicine_adapter.CustomViewHolder>{
    private ArrayList<Medicine_text> mList;
    private List<Medicine_text> gList;

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
        protected TextView tv_todayMedicine;
        protected TextView tv_todayMedicineResult;
        protected Button btn_delete;



        public CustomViewHolder(View view) {
            super(view);
            this.tv_todayMedicine = (TextView) view.findViewById(R.id.tv_todayMedicine);
            this.tv_todayMedicineResult = (TextView) view.findViewById(R.id.tv_todayMedicineResult);


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
//            // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다.
//            // ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.
//            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
//            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
//        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case 1002:
//                        mList.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), mList.size());
//
//                        break;
//                }
//                return true;
//            }
//        };

    }

    public Medicine_adapter(ArrayList<Medicine_text> list) {
        this.mList = list;
    }
    public Medicine_adapter(Context mContext, List<Medicine_text> list) {
        this.mContext = mContext;
        this.gList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.medicine_onelist, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    public void removeItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.tv_todayMedicine.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        viewholder.tv_todayMedicineResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewholder.tv_todayMedicine.setGravity(Gravity.CENTER);
        //viewholder.tv_todayMedicineResult.setGravity(Gravity.CENTER);

        viewholder.tv_todayMedicine.setText("약 복용");
        String seeText = mList.get(position).getmealStatus();
        //if (seeText.length() >= 25){seeText = seeText.substring(0,25);};
        //viewholder.tv_todayCleanResult.setText(seeText+" ···");
        Date today_date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 M월 dd일", Locale.getDefault());
        seeText = format.format(today_date)+" "+mList.get(position).gettime()+mList.get(position).getmealStatus()
        +mList.get(position).getmedicine();
        viewholder.tv_todayMedicineResult.setText(seeText);



    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
