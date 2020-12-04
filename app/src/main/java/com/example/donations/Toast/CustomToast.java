package com.example.donations.Toast;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donations.R;

public class CustomToast extends View{
    Context mContext;
    public CustomToast(Context context) {
        super(context);
        mContext = context;
    }

    public void SuccessToast(String message){
        LayoutInflater  inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View layout = inflater.inflate(R.layout.toast_design,
            (ViewGroup) findViewById(R.id.toast_layout_root));
    layout.setBackgroundColor(Color.parseColor("#7cbf62"));
    ImageView image = (ImageView) layout.findViewById(R.id.image);
    image.setImageResource(R.drawable.toast_success);
    TextView text = (TextView) layout.findViewById(R.id.text);
    text.setText(message);

    Toast toast = new Toast(mContext.getApplicationContext());
    toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
    toast.setDuration(Toast.LENGTH_LONG);
    toast.setView(layout);
    toast.show();
}
    public void ErrorToast(String message){
        LayoutInflater  inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_design,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        layout.setBackgroundColor(Color.parseColor("#bf626b"));
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.tick_error);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(mContext.getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}

