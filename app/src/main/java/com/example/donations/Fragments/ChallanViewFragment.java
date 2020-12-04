package com.example.donations.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallanViewFragment extends Fragment {

    TextView name, phone, date, type, description, amount;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    Button save_image;
    public static Context mcontext;

    public ChallanViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View challanView = inflater.inflate(R.layout.fragment_challan_view, container, false);

        mcontext = getContext();
        name = challanView.findViewById(R.id.name_view);
        phone = challanView.findViewById(R.id.phone_view);
        date = challanView.findViewById(R.id.date_view);
        type = challanView.findViewById(R.id.cnic_view);
        description = challanView.findViewById(R.id.description_challan);
        amount = challanView.findViewById(R.id.amount_challan);
        save_image = challanView.findViewById(R.id.save_img);

        name.setText(LoginFragment.username);
        phone.setText(LoginFragment.userphone);
        date.setText(GenerateChallanFragment.date);
        description.setText(GenerateChallanFragment.description);
        amount.setText(GenerateChallanFragment.amount);
        type.setText(GenerateChallanFragment.type);

        save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout viewlinear = challanView.findViewById(R.id.linear_save);
                viewlinear.setDrawingCacheEnabled(true);
                viewlinear.buildDrawingCache();
                Bitmap bm = viewlinear.getDrawingCache();
                SaveImage(bm);
                sendDataServer();
            }
        });

        return challanView;
    }

    public static void saveChallan(){
        new CustomToast(mcontext).SuccessToast("Challan Saved");

    }
    public static void showError(){
        new CustomToast(mcontext).SuccessToast("Request Error");
    }

    private void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        Log.e("abc", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendDataServer(){
        SendData sendData = new SendData(getContext());
        sendData.addDonation(LoginFragment.userid, GenerateChallanFragment.date, GenerateChallanFragment.amount, GenerateChallanFragment.description, GenerateChallanFragment.type);
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {


        }
    }

}
