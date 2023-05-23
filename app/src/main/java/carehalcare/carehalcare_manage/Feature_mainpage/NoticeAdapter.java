package carehalcare.carehalcare_manage.Feature_mainpage;

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

    private String formatDate(String dateStr) {

        if (dateStr == null) {
            return ""; // 또는 다른 기본값을 반환할 수 있음
        }

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String newDate = "";
        try {
            Date date = originalFormat.parse(dateStr);
            newDate = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

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
