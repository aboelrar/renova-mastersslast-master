package com.example.lenovo.renova.library;

        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.RadioButton;

        import com.example.lenovo.renova.R;
        import com.example.lenovo.renova.view.searchActivity;

        import java.util.Timer;
        import java.util.TimerTask;

public class progressdialog {
    RadioButton mostRated,hieghtPrice,lowestPrice;

    public void progressDialog(Context context)
    {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               pd.dismiss();
            }
        },500,1500);
    }
    public void dialog(Context context, int resource, final String param2 )
    {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        mostRated=(RadioButton)dialog.findViewById(R.id.mostrated);
        mostRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button close=(Button)dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void dialogProgress(Context context, int resource, final String param2 )
    {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(resource);
        int width = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },2000,2500);

    }


}
