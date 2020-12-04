package com.example.donations.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.donations.R;
import com.example.donations.Toast.CustomToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenerateChallanFragment extends Fragment {

    EditText challan_type, date_picker_add_challan, amount_add_challan, description_add_challan;
    public static String type, date, amount, description;
    Button btn_add_challan;
    Dialog dialog;
    RadioGroup rg;
    int selected_index = -1;

    public GenerateChallanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View generateChallan = inflater.inflate(R.layout.fragment_generate_challan, container, false);

        showRadioButtonDialog();

        challan_type = generateChallan.findViewById(R.id.select_type_add_bill);
        date_picker_add_challan = generateChallan.findViewById(R.id.select_date_add_bill);
        amount_add_challan = generateChallan.findViewById(R.id.amount_add_bill);
        description_add_challan = generateChallan.findViewById(R.id.description_add_bill);
        btn_add_challan= generateChallan.findViewById(R.id.btn_add_bill);

        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date current_date = Calendar.getInstance().getTime();
        date_picker_add_challan.setText(dateformat.format(current_date));

        date_picker_add_challan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        challan_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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

    private void showDatePicker(){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(getActivity(), R.style.DateTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date_picker_add_challan.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }

    private void showRadioButtonDialog() {

        String[] items = {"Challan"};

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
                        challan_type.setText(btn.getText());
                    }
                }
            }
        });

    }

    public void checkFields(){
        date = date_picker_add_challan.getText().toString();
        amount = amount_add_challan.getText().toString();
        description = description_add_challan.getText().toString();
        type = challan_type.getText().toString();

        if(TextUtils.isEmpty(date)){
            new CustomToast(getContext()).ErrorToast("date not found");
        }
        else if(TextUtils.isEmpty(amount)){
            new CustomToast(getContext()).ErrorToast("Amount not found");
        }
        else if(TextUtils.isEmpty(description)){
            new CustomToast(getContext()).ErrorToast("Description not found");
        }
        else if(TextUtils.isEmpty(type)){
            new CustomToast(getContext()).ErrorToast("Transfer type not found");
        }
        else{
            generateChallan();
        }
    }

    public void generateChallan(){
        loadFragment(new ChallanViewFragment());
    }

    private boolean loadFragment(Fragment fragment) { //Switch fragments
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up) // Fade in/out animation
                    .replace(R.id.frame_donor, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
