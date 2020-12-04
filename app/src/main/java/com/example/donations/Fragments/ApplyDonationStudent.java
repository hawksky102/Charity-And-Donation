package com.example.donations.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyDonationStudent extends Fragment {
    static EditText sudo_date, sudo_description, sudo_amount, sudo_current_semester, sudo_apply_for;
    String date, description, amount, current_semester, apply_for;
    ImageView imageView;
    static int id;
    Button btn_add_student;
    ImageView upload_bill_btn;
    Dialog dialog;
    Dialog dialog_apply;
    RadioGroup rg;
    RadioGroup rg_apply;
    boolean checkImageSelected = false;
    int selected_index = -1;
    int selected_index_apply = -1;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static Bitmap bitmap;
    private String filePath;
    public static Context mcontext;

    public ApplyDonationStudent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View applyStudent = inflater.inflate(R.layout.fragment_apply_donation_student, container, false);

        mcontext = getContext();
        showRadioButtonDialogCurrent();
        showRadioButtonDialogApply();

        sudo_date = applyStudent.findViewById(R.id.sudo_date);
        sudo_amount = applyStudent.findViewById(R.id.sudo_amount);
        sudo_description = applyStudent.findViewById(R.id.sudo_description);
        sudo_current_semester = applyStudent.findViewById(R.id.sudo_current);
        sudo_apply_for = applyStudent.findViewById(R.id.sudo_apply_for);
        imageView = applyStudent.findViewById(R.id.upload_bill_btn);
        upload_bill_btn = applyStudent.findViewById(R.id.upload_bill_btn);
        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date current_date = Calendar.getInstance().getTime();
        sudo_date.setText(dateformat.format(current_date));

        sudo_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        btn_add_student = applyStudent.findViewById(R.id.btn_add_student);

        sudo_current_semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        sudo_apply_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_apply.show();
            }
        });
        btn_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
            }
        });



        return applyStudent;
    }
    private void showDatePicker(){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(getActivity(), R.style.DateTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                sudo_date.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }
    private void showRadioButtonDialogCurrent() {

        String[] items = {"1", "2", "3", "4", "5", "6", "7", "8"};

        dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_radio_addbill);
        rg = dialog.findViewById(R.id.radio_group);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked}, //disabled
                        new int[]{android.R.attr.state_checked} //enabled
                },
                new int[] {
                        Color.parseColor("#858585"), //disabled
                        Color.parseColor("#58B1FF") //enabled
                }
        );
        for(int i=0;i<items.length;i++){
            RadioButton rb = new RadioButton (getActivity());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i==0){
                params.setMargins(8, 5, 15, 10);
            }
            else{
                params.setMargins(8, 7, 15, 10);
            }
            rb.setLayoutParams(params);
            rb.setPaddingRelative(15,0,0,0);
            rb.setText(items[i]);
            rb.setTextSize(16);
            rb.setButtonTintList(colorStateList);
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        selected_index = btn.getId();
                        dialog.cancel();
                        sudo_current_semester.setText(btn.getText());
                    }
                }
            }
        });

    }
    private void showRadioButtonDialogApply() {

        String[] items = {"1", "2", "3", "4", "5", "6", "7", "8"};

        dialog_apply = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog_apply.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_apply.setContentView(R.layout.custom_radio_addbill);
        rg_apply = dialog_apply.findViewById(R.id.radio_group);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked}, //disabled
                        new int[]{android.R.attr.state_checked} //enabled
                },
                new int[] {
                        Color.parseColor("#858585"), //disabled
                        Color.parseColor("#58B1FF") //enabled
                }
        );
        for(int i=0;i<items.length;i++){
            RadioButton rb = new RadioButton (getActivity());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i==0){
                params.setMargins(8, 5, 15, 10);
            }
            else{
                params.setMargins(8, 7, 15, 10);
            }
            rb.setLayoutParams(params);
            rb.setPaddingRelative(15,0,0,0);
            rb.setText(items[i]);
            rb.setTextSize(16);
            rb.setButtonTintList(colorStateList);
            rg_apply.addView(rb);
        }
        rg_apply.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        selected_index_apply = btn.getId();
                        dialog_apply.cancel();
                        sudo_apply_for.setText(btn.getText());
                    }
                }
            }
        });

    }
    public void checkFields(){
        date = sudo_date.getText().toString();
        description = sudo_description.getText().toString();
        amount = sudo_amount.getText().toString();
        current_semester = sudo_current_semester.getText().toString();
        apply_for = sudo_apply_for.getText().toString();

        if(TextUtils.isEmpty(date)){
            new CustomToast(getContext()).ErrorToast("Date not found");
        }
        else if(TextUtils.isEmpty(description)){
            new CustomToast(getContext()).ErrorToast("Description not found");
        }
        else if(TextUtils.isEmpty(amount)){
            new CustomToast(getContext()).ErrorToast("Amount not found");
        }
        else if(TextUtils.isEmpty(current_semester)){
            new CustomToast(getContext()).ErrorToast("Current semester not found");
        }
        else if(TextUtils.isEmpty(apply_for)){
            new CustomToast(getContext()).ErrorToast("Apply semester not found");
        }
        else{
            sendRequestData();
        }

    }
    public void sendRequestData(){
        SendData sendData = new SendData(getContext());
        sendData.addStudentRequest(LoginFragment.userid, date, description, amount, current_semester, apply_for);
    }
    public static void onSucessRequest(){
        new CustomToast(mcontext).SuccessToast("Request Submitted");
        sudo_description.setText("");
        sudo_amount.setText("");
        sudo_current_semester.setText("");
        sudo_apply_for.setText("");
    }
    public static void onErrorRequest(){
        new CustomToast(mcontext).ErrorToast("Request Error");
    }

}
