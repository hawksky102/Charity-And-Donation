package com.example.donations.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;
import com.example.donations.TouchView.TouchImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPaymentDonor extends Fragment {
    EditText challan_type, date_picker_add_challan, amount_add_challan, description_add_challan;
    String type, date, amount, description;
    ImageView imageView;
    static int id;
    Button btn_add_challan;
    ImageView upload_bill_btn;
    Dialog dialog;
    RadioGroup rg;
    boolean checkImageSelected = false;
    int selected_index = -1;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static Bitmap bitmap;
    private String filePath;
    public static Context mcontext;

    public AddPaymentDonor(int idGet, String dateGet, String descriptionGet, String amountGet, String typeGet) {
        id = idGet;
        date = dateGet;
        amount = amountGet;
        type = typeGet;
        description = descriptionGet;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View generateChallan = inflater.inflate(R.layout.fragment_add_payment_donor, container, false);

        //showRadioButtonDialog();
        mcontext = getActivity();
        challan_type = generateChallan.findViewById(R.id.select_type_add_bill);
        date_picker_add_challan = generateChallan.findViewById(R.id.select_date_add_bill);
        amount_add_challan = generateChallan.findViewById(R.id.amount_add_bill);
        description_add_challan = generateChallan.findViewById(R.id.description_add_bill);
        btn_add_challan = generateChallan.findViewById(R.id.btn_add_bill);
        imageView = generateChallan.findViewById(R.id.upload_bill_btn);
        upload_bill_btn = generateChallan.findViewById(R.id.upload_bill_btn);

        challan_type.setText(type);
        date_picker_add_challan.setText(date);
        amount_add_challan.setText(amount);
        description_add_challan.setText(description);

        upload_bill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });


        btn_add_challan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
            }
        });

        return generateChallan;
    }
    public void checkPermission(){
        if ((ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            showFileChooser();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
//                showSelectedImage(picUri, filePath);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageURI(picUri);
                checkImageSelected = true;
            }
            else
            {
                Toast.makeText(
                        getActivity(),"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }

    }


    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public void checkFields(){
        if(!checkImageSelected){
            new CustomToast(getContext()).ErrorToast("Please provide image proof");
        }
        else{
            sendDataServer(true, "");
        }
    }
    public static void sendDataServer(boolean checkOption, String filename) {
        if(checkOption){
            SendData sendData = new SendData(mcontext);
            sendData.uploadBitmapAmount(bitmap);
        }
        else {
            SendData sendData = new SendData(mcontext);
            sendData.addPayment(id, filename);
        }

    }
    public static void checkResponse(){
        new CustomToast(mcontext).SuccessToast("Payment Verified");
        loadFragment(new PendingPaymentDonor());

    }
    public static void showError(){
        new CustomToast(mcontext).SuccessToast("Request Error");
    }

    private static boolean loadFragment(Fragment fragment) { //Switch fragments
        //switching fragment
        if (fragment != null) {
            ((FragmentActivity)mcontext).getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_botton_up, R.anim.slide_bottom_down) // Fade in/out animation
                    .replace(R.id.frame_donor, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
