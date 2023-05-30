package carehalcare.carehalcare_manage.Feature_carereport;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import carehalcare.carehalcare_manage.R;

public class LoadingDialog extends Dialog {
    private Context context;
    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
    }
}
