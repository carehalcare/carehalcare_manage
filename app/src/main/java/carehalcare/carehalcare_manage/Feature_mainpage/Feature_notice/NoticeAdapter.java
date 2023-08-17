package carehalcare.carehalcare_manage.Feature_mainpage.Feature_notice;

import static carehalcare.carehalcare_manage.Feature_carereport.DateUtils.formatDate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import carehalcare.carehalcare_manage.R;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private List<Notice> notices;
    private OnItemClickListener onItemClickListener;

    public NoticeAdapter (List<Notice> notices){ this.notices = notices; };

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_list, parent, false);
        NoticeAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notice notice = notices.get(position);
        // Content 설정
        String content = notice.getContent();
        String date = notice.getModifiedDate();
        String formattedDate = formatDate(date);

        holder.tv_notititle.setText(content);
        holder.tv_notidate.setText(formattedDate);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_notititle, tv_notidate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_notititle = (TextView) itemView.findViewById(R.id.tv_notititle);
            tv_notidate = (TextView) itemView.findViewById(R.id.tv_notidate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                onItemClickListener.onItemClick(position); // 클릭 리스너 콜백 호출

            }
        }
    }

    // 외부에서 클릭 리스너 설정하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // 클릭 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() { return notices.size(); }
}
